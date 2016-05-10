using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.common;
using soox.ooxml.elements;

namespace soox.ooxml.core
{
    abstract class Block : OoxmlElement, IBlock
    {
        protected XSize _size = new XSize(0.0f, 0.0f);

        public void setWidth(float width)
        {
            this._size.Width = width;
        }

        public void setHeight(float height)
        {
            this._size.Height = height;
        }

        public List<Run> getRuns()
        {
            List<Run> runs = new List<Run>();
            foreach (OoxmlElement elem in this.getChildren())
            {
                if (elem is Block)
                {
                    runs.AddRange((elem as Block).getRuns());
                }
                else if (elem.getTagName().Equals(Run.TAG_NAME))
                {
                    runs.Add(elem as Run);
                }
            }

            return runs;
        }

        public Block getInnerBlock()
        {
            foreach (OoxmlElement elem in this.getChildren())
            {
                if (elem is Block)
                {
                    return elem as Block;
                }
            }

            return null;
        }

        public List<IEntity> getEntities()
        {
            List<Run> runs = getRuns();

            List<IEntity> entities = new List<IEntity>(runs.Count);
            for (int i = 0; i < runs.Count; i++)
            {
                entities.Add(runs[i] as IEntity);
            }

            return entities;
        }

    }
}
