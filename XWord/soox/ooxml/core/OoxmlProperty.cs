using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using soox.ooxml.elements;

namespace soox.ooxml.core
{
    public abstract class OoxmlProperty : OoxmlElement
    {
        public virtual void DecorateOwner(OoxmlElement owner)
        {
            for (int i = 0; i < this._children.Count; i++)
            {
                IStyle style = this._children[i] as IStyle;
                if (null == style)
                {
                    continue;
                }

                style.Decorate(owner);
                //this._children[i].ApplyOoxmlStyles(xmlElement);
            }
        }
    }
}



















































































































































