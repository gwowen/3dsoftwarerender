import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.map;

public class OBJModel
{
  private class OBJIndex
  {
      private int m_vertexIndex;
      private int m_texCoordIndex;
      private int m_normalIndex;

      public int GetVertexIndex() { return m_vertexIndex; }
      public int GetTexCoordIndex() { return m_texCoordIndex; }
      public int GetNormalIndex() { return m_normalIndex; }

      public void SetVertexIndex(int val) { m_vertexIndex = val; }
      public void SetTexCoordIndex(int val) { m_texCoordIndex = val; }
      public void SetNormalIndex(int val) { m_normalIndex = val; }

      @Override
      public boolean equals(Object obj)
      {

      }

      @Override
      public int hashCode()
      {

      }
  }

  private List<Vec4f> m_positions;
  private List<Vec4f> m_texCoord;
  private List<Vec4f> m_normals;
  private List<OBJIndex> m_indices;
  private boolean m_hasTexCoords;
  private boolean m_hasNormals;

  private static String[] RemoveEmptyStrings(String[] data)
  {

  }

  public OBJModel(String filename) throws IOException
  {

  }

  public IndexedModel ToIndexedModel()
  {

  }

  private OBJIndex ParseOBJIndex(String token)
  {

  }
}
