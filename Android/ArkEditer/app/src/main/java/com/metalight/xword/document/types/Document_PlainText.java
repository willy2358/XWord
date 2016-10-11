package com.metalight.xword.document.types;

import java.io.FileInputStream;

//import org.apache.http.util.EncodingUtils;

import com.metalight.xword.document.elements.Document_Page;

public class Document_PlainText extends Document {
	
	public Document_PlainText(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	public boolean loadContents()
	{
		try
		{
			FileInputStream fin = new FileInputStream(this.getFileName());
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			
			String txt = "aaaaa";//EncodingUtils.getString(buffer, "UTF-8");////��Y.txt�ı�������ѡ����ʵı��룬�����������
			
			//remain refine
			Document_Page page = new Document_Page();
			if (page.parseParagraphs(txt)){
				this._pages.add(page);
			}
			
			fin.close();
            return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
}
