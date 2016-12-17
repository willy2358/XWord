using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace soox.serialize
{
    public class JsonSerializer
    {
        public static string DictToJson(Dictionary<String, String> dict)
        {
            string json = "[";
            foreach (var v in dict)
            {
                json += string.Format("\"{0}\":{1},", v.Key, ToJsonValueString(v.Value));
            }
            return json.TrimEnd(',') + "]";

        }

        private static string ToJsonValueString(Object value)
        {
            if (value is string)
            {
                return "\"" + value.ToString() + "\"";
            }
            else
            {
                return value.ToString();
            }
        }

        public static string ToJson<ISerializable>(List<ISerializable> objs)
        {
            string json = "[";
            for (int i = 0; i < objs.Count; i++ )
            {
                ISerializable obj = objs[i];
                json += ToJson(obj);
                if (i < objs.Count - 1)
                {
                    json += ",";
                }
            }

            json += "]";
            return json;
        }

        private static string ToJson(Object Obj)
        {
            string json = "{";
            Type t = Obj.GetType();
            bool firsted = false;
            foreach (PropertyInfo p in t.GetProperties())
            {
                var attrs = p.GetCustomAttributes(typeof(JsonSerializeAttribute), false);
                if (null == attrs || attrs.Length < 1)
                {
                    continue;
                }

                if (!firsted)
                {
                    firsted = true;
                }
                else
                {
                    json += ",";
                }
                string key = string.IsNullOrEmpty((attrs[0] as JsonSerializeAttribute).Key) ? p.Name : (attrs[0] as JsonSerializeAttribute).Key;
                string ret = ToJsonForProperty(Obj, p, key);
                if (null != ret)
                {
                    json += ret;
                }
            }
            json += "}";
            return json;
        }

        private static string ToJsonForProperty(Object obj, PropertyInfo prop, string key)
        {
            if (!prop.CanRead)
            {
                return null;
            }
            string json = string.Format("\"{0}\":", key);
            if (prop.PropertyType == typeof(double) || prop.PropertyType == typeof(int)
                ||prop.PropertyType == typeof(float) || prop.PropertyType == typeof(long)
                ||prop.PropertyType == typeof(UInt32))
            {
                Object ret = prop.GetValue(obj);
                json += string.Format("{0}", ret);
            }
            else if (prop.PropertyType == typeof(string))
            {
                Object ret = prop.GetValue(obj);
                json += string.Format("\"{0}\"", ret);
            }
            else if ((prop.PropertyType.IsGenericType && prop.PropertyType.GetGenericTypeDefinition() == typeof(List<>))
                     ||(prop.PropertyType.BaseType.IsGenericType && prop.PropertyType.BaseType.GetGenericTypeDefinition() == typeof(List<>)))
            {
                Type genericType = prop.PropertyType.IsGenericType ? prop.PropertyType : prop.PropertyType.BaseType;
                //To refine: is it possible to remove hard code of user.XLineBlock
                if (genericType.GenericTypeArguments[0] == typeof(user.XLineBlock))
                {
                    List<user.XLineBlock> blocks = (List<user.XLineBlock>)prop.GetValue(obj);
                    json += ToJson<user.XLineBlock>(blocks);
                }
            }
            else
            {
                Object ret = prop.GetValue(obj);
                json += ToJson(ret);
            }
            return json;
        }
    }
}
