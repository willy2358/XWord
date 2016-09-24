using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.render;
using soox.opc;
using soox.ooxml.core;
using soox.ooxml.elements;
using soox.common;
using soox.metrics;
using soox.xps.parts;
using soox.xps.elements;
using System.IO;

namespace soox.user
{
    public class XDocument
    {
        public enum Edit_Type { Delete = 1, Insert, Replace}
        private Paint _paint = null;
        private List<XPage> _pages = null;
        private List<XPage2> _pages2 = new List<XPage2>();
        private OPCPackage _Package = null;
        private Part _mainPart = null;

        private List<Part_fpage> _xpsPages = new List<Part_fpage>();
        public XDocument(String document)
        {
            this._Package = new OPCPackage(document);

            this._mainPart = this._Package.getEntryPart();
        }

        public void ParseContents()
        {
            this._mainPart.getContents();
        }

        public void SetupIdForRuns()
        {
            int runId = 1;
            List<IBlock> blocks = this._mainPart.getContents();
            for (int i = 0; i < blocks.Count; i++)
            {
                Block block = blocks[i] as Block;
                List<Run> runs = block.getRuns();
                for (int j = 0; j < runs.Count; j++)
                {
                    Run run = runs[j];
                    run.RunId = runId++;
                }
            }
        }
 
        public void setPaint(Paint paint)
        {
            this._paint = paint;
        }


        public Paint getPaint()
        {
            return this._paint;
        }

        public void drawPage(int pageNum)
        {
            if (null == this._pages)
            {
                this._pages = new List<XPage>();
                parsePages();
            }

            if (pageNum < 0 || pageNum > this._pages.Count)
            {
                return;
            }

            this._pages[pageNum - 1].draw(this._paint);
        }

        public bool saveAs(String newFileName)
        {
            if (File.Exists(newFileName))
            {
                try
                {
                    File.Delete(newFileName);
                }
                catch(Exception)
                {
                    return false;
                }
            }

            this._mainPart.ApplyUpdatesToDataStream();
            return this._Package.saveAs(newFileName);
        }

        private XPage getPage(int pageNum)
        {

            if (pageNum > 0 && pageNum <= this._pages.Count)
            {
                return this._pages[pageNum];
            }

            return null;
        }

        private void parseParagraphIntoTextLines(float pageWidth, Paragraph para, float paraYPosBase, ref List<XTextLine> lines, ref float paragraphHeight)
        {
            List<Run> runs = getParagraphRuns(para);
            if (null == runs || runs.Count < 1)
            {
                return;
            }

            paragraphHeight = 0;
            float width = 0;
            XTextLine curLine = new XTextLine();
            lines.Add(curLine);
            for (int i = 0; i < runs.Count; i++)
            {
                Run r = runs[i];
                XSize size = MeasureRunTextSize(r, para.Property);
                if (width + size.Width > pageWidth)
                {
                    paragraphHeight += curLine.getTextHeight();

                    curLine = new XTextLine();
                    width = 0;
                    lines.Add(curLine);
                }

                r.setPos(width, paragraphHeight + paraYPosBase);
                r.setSize(size.Width, size.Height);
                width += size.Width;
                curLine.AddRun(r);
            }
            paragraphHeight += curLine.getTextHeight();

            //应用段对齐
            AdjustLastLinePositionByParagraphAlignType(pageWidth, para, lines);
        }

        private XSize MeasureRunTextSize(Run run, pPr paraProperty)
        {
            XSize metric = FontMetrics.GetFontMetrics(run.getFontName(), run.getFontSize(), paraProperty.Spacing.LineSpacing);
            if (null == metric)
            {
                metric = new XSize(10.5f, 15.6f);
            }

            if (!string.IsNullOrEmpty(run.getText()))
            {
                return new XSize(metric.Width * run.getText().Length, metric.Height);
            }
            else
            {
                return new XSize(0.0f, 0.0f);
            }
        }

        private List<Run> getParagraphRuns(Paragraph para)
        {
            Block block = para.getInnerBlock();
            if (null != block)
            {
                return block.getRuns();
            }
            else
            {
                return para.getRuns();
            }
        }

        private void AdjustLastLinePositionByParagraphAlignType(float pageWidth, Paragraph para, List<XTextLine> lines)
        {
            //默认为左对齐
            if (lines.Count > 0 && para.Property.AlignType != pPr.Align_Type.start)
            {
                XTextLine lastLine = lines[lines.Count - 1];
                if (lastLine.getWidth() >= pageWidth)
                {
                    return;
                }

                if (para.Property.AlignType == pPr.Align_Type.center)
                {
                    float offset = (pageWidth - lastLine.getWidth()) / 2;
                    lastLine.moveXPos(offset);
                }
                else if (para.Property.AlignType == pPr.Align_Type.end)
                {
                    lastLine.moveXPos(pageWidth - lastLine.getWidth());
                }
            }
        }

        private List<XTextLine> parseParagraphTextLines(float pageWidth, Paragraph para, float XPosBase)
        {
            List<Run> runs = getParagraphRuns(para);
            if (null == runs || runs.Count < 1)
            {
                return null;
            }

            List<XTextLine> lines = new List<XTextLine>();
            float width = 0;
            XTextLine curLine = new XTextLine();
            lines.Add(curLine);
            for (int i = 0; i < runs.Count; i++)
            {
                Run r = runs[i];
                XSize size = MeasureRunTextSize(r, para.Property);
                if (width + size.Width > pageWidth)
                {
                    curLine = new XTextLine();
                    width = 0;
                    lines.Add(curLine);
                }
                r.setXPos(XPosBase + width);
                r.setSize(size.Width, size.Height);
                width += size.Width;
                curLine.AddRun(r);
            }

            for (int i = 0; i < lines.Count - 1; i++)
            {
                setTextLineHeight(lines[i], lines[i + 1], para.Property.Spacing.LineSpacing);
            }

            //应用段对齐
            AdjustLastLinePositionByParagraphAlignType(pageWidth, para, lines);
            return lines;
        }

        public void PrintPages()
        {
            for(int i = 0; i < this._pages2.Count; i++)
            {
                System.Diagnostics.Debug.WriteLine("-----------------------------------Page:{0}", i + 1);
                XPage2 page = this._pages2[i];
                for(int j = 0; j < page.LineBlocks.Count;j++)
                {
                    XLineBlock block = page.LineBlocks[j];
                    System.Diagnostics.Debug.WriteLine("####{0},{1}:{2}", block.Position.X, block.Position.Y, block.Text);
                }
            }
        }

        public bool UpdatePageText(int pageIdx, int runId,  Edit_Type editType, string oldText, string newText, string editTrack)
        {
            System.Diagnostics.Debug.Assert(pageIdx >= 0 && pageIdx < this._pages2.Count);

            if (this._pages2[pageIdx].UpdateRunText(runId, oldText, newText))
            {
                LogDocUpdateHistory(editType);
                return true;
            }

            return false;
        }

        private void LogDocUpdateHistory(Edit_Type editType)
        {

        }

        public List<XPage2> GetPages(int startPageIdx, int endPageIdx)
        {
            List<XPage2> pages = new List<XPage2>();
            if (startPageIdx < 0)
            {
                startPageIdx = 0;
            }
            if (endPageIdx > this._pages2.Count - 1)
            {
                endPageIdx = this._pages2.Count - 1;
            }

            return this._pages2.GetRange(startPageIdx, endPageIdx - startPageIdx + 1);
        }
        private void setTextLineHeight(XTextLine line, XTextLine nextLine, float lineSpacing)
        {
            if (null == line || null == nextLine)
            {
                return;
            }

            float fontSize1 = line.getFontSize();
            float fontSize2 = nextLine.getFontSize();
            XSize size = FontMetrics.GetFontMetrics(line.getFontName(), fontSize1, fontSize2, lineSpacing);
            if (null != size)
            {
                line.setLineHeight(size.Height);
            }
        }

        private void setParagraphTextLinesHeightAndPosition(List<XTextLine> lines, float paraYPosBase, float lineSpacing)
        {
            if (null == lines)
            {
                return;
            }

            for (int i = 0; i < lines.Count - 1; i++)
            {
                setTextLineHeight(lines[i], lines[i + 1], lineSpacing);
            }

            float offset = paraYPosBase;
            for (int i = 0; i < lines.Count; i++)
            {
                XTextLine line = lines[i];
                line.setYPos(offset);

                offset += line.getLineHeight();
            }
        }

        public void parsePagesFromXPS(string xpsFile)
        {
            ParseXPSPages(xpsFile);

            parseUserPages();
        }

        private void ParseXPSPages(string xpsFile)
        {
            OPCPackage_XPS xpsOpc = new OPCPackage_XPS(xpsFile);
            Part part = xpsOpc.GetFixedDocPart();
            List<IBlock> blocks = part.getContents();
            int pageNum = blocks.Count;
            for (int i = 0; i < blocks.Count; i++)
            {
                PageContent page = blocks[i] as PageContent;
                string pagePartPath = part.getZipDir() + page.GetMyPagePartPath();
                Part_fpage pagePart = xpsOpc.GetPartByPath(pagePartPath) as Part_fpage;
                this._xpsPages.Add(pagePart);
            }
        }

        private void parseUserPages()
        {
            int pageIdx = 0;
            int glyphIdx = 0;
            List<IBlock> blocks = this._mainPart.getContents();
            XPage2 _currentPage = new XPage2(pageIdx);
            for (int i = 0; i < blocks.Count; i++)
            {
                Block block = blocks[i] as Block;
                List<Run> runs = block.getRuns();
                for(int j = 0; j < runs.Count; j++)
                {
                    Run run = runs[j];
                    if (null == run.getText() || run.getText().Trim().Length < 1)
                    {
                        continue;
                    }
                    System.Diagnostics.Debug.WriteLine(string.Format("------------------{0}", run.getText()));
                    int pageIdxBak = pageIdx;
                    List<Glyphs> listGlyphs = getGlyphsesForRun(run, ref pageIdx, ref glyphIdx);
                    glyphIdx++;
                    if (pageIdx > pageIdxBak)
                    {
                        this._pages2.Add(_currentPage);
                        _currentPage = new XPage2(pageIdx);
                    }

                    for(int k = 0; k < listGlyphs.Count; k++)
                    {
                        XLineBlock lineBlock = new XLineBlock(run, listGlyphs[k].getPosition(), listGlyphs[k].getText());
                        _currentPage.AddLineBlock(lineBlock);
                    }

                }
            }
            this._pages2.Add(_currentPage);
        }

        private List<Glyphs> getGlyphsesForRun(Run run, ref int startPageIdx, ref int startGlyphIdx)
        {
            List<Glyphs> glyphses = new List<Glyphs>();
            bool finish = false;
            string matchedText = "";
            for(int i = startPageIdx; i< this._xpsPages.Count && !finish; i++)
            {
                Part_fpage fpage = this._xpsPages[i];
                List<Glyphs> gs = fpage.GetAllGlyphs();
                for(int j = startGlyphIdx; j< gs.Count; j++)
                {
                    if (gs[j].getText() == run.getText()
                        || gs[j].getText().Contains(run.getText()))
                    {
                        glyphses.Add(gs[j]);
                        finish = true;
                    }
                    else if (run.getText().Contains(gs[j].getText())
                             )
                    {
                        glyphses.Add(gs[j]);
                        matchedText += gs[j].getText();
                        if (matchedText.Length >= run.getText().Length)
                        {
                            finish = true;
                        }
                    }
                    else if (gs[j].isAllSpaces() && run.getText().Contains(gs[j].getChineseSpaces()))
                    {
                        glyphses.Add(gs[j]);
                        matchedText += gs[j].getChineseSpaces();
                        if (matchedText.Length >= run.getText().Length)
                        {
                            finish = true;
                        }
                    }
                    else if (glyphses.Count > 0)
                    {
                        finish = true;
                    }

                    if (finish)
                    {
                        startPageIdx = i;
                        startGlyphIdx = j;
                        break;
                    }
                }
                if (!finish)
                {
                    startGlyphIdx = 0;
                }
            }
            return glyphses;
        }

        private Glyphs getGlyphsForRunFromXpsPage(Run run, ref int startPageIdx, ref int startGlyphIdx, out string text)
        {
            text = "";
            for(int i = startPageIdx; i < this._xpsPages.Count; i++)
            {
                Part_fpage fpage = this._xpsPages[i];
                List<Glyphs> gs = fpage.GetAllGlyphs();
                for(int j = startGlyphIdx; j < gs.Count; j++)
                {
                    if (run.getText().Contains(gs[j].getText()) )
                    {
                        startPageIdx = i;
                        startGlyphIdx = j;
                        text = gs[j].getText();
                        return gs[j];
                    }
                    else if (gs[j].getText().Contains(run.getText()))
                    {
                        startPageIdx = i;
                        startGlyphIdx = j - 1;
                        text = run.getText();
                        return gs[j];
                    }
                }

                startGlyphIdx = 0;
            }

            return null;
        }



        private bool IsParagraphInXpsPage(Paragraph p, Part_fpage page)
        {
            return true;
        }
        private void parsePages()
        {
            //Todo 用sectPr中的页面设置
            float pageWidth = (11906 - 1800) * 0.05f;
            float pageHeight = (16838 - 1440) * 0.05f;
            float YPosBase = 90.0f;
            float XPosBase = 72.0f;
            List<XTextLine> allLines = splitAllParagraphsIntoTextLines(pageWidth, XPosBase, YPosBase);

            splitAllTextLinesIntoPages(pageHeight, YPosBase, allLines);
        }

        private void splitAllTextLinesIntoPages(float pageHeight, float YPosBase, List<XTextLine> allLines)
        {
            XPage curPage = null;
            int curPageNum = 0;
            for (int j = 0; j < allLines.Count; j++)
            {
                XTextLine line = allLines[j];
                if (line.getYPos() > (curPageNum * pageHeight + YPosBase - 1))
                {
                    curPage = new XPage();
                    this._pages.Add(curPage);
                    curPageNum++;
                }

                curPage.addTextLine(line);
            }
        }

        private List<XTextLine> splitAllParagraphsIntoTextLines(float pageWidth, float XPosBase, float YPosBase)
        {
            float paraYPosBase = YPosBase;

            List<IBlock> blocks = this._mainPart.getContents();
            XTextLine lastLineOfLastPara = null;
            List<XTextLine> allLines = new List<XTextLine>();
            for (int i = 0; i < blocks.Count; i++)
            {
                Paragraph para = blocks[i] as Paragraph;
                if (null == para)
                {
                    continue;
                }

                List<XTextLine> lines = parseParagraphTextLines(pageWidth, para, XPosBase);
                if (null == lines)
                {
                    continue;
                }

                if (null != lastLineOfLastPara && lines.Count > 0)
                {
                    setTextLineHeight(lastLineOfLastPara, lines[0], para.Property.Spacing.LineSpacing);
                    paraYPosBase = lastLineOfLastPara.getLineHeight() + lastLineOfLastPara.getYPos();
                }

                setParagraphTextLinesHeightAndPosition(lines, paraYPosBase, para.Property.Spacing.LineSpacing);
                lastLineOfLastPara = lines[lines.Count - 1];
                allLines.AddRange(lines);
            }
            return allLines;
        }
    }
}
