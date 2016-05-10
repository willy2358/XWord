using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using soox.common;

namespace soox.ooxml.core
{
    public interface IBlock
    {
        List<IEntity> getEntities();

    }
}
