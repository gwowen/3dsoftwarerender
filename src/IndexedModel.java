import java.util.ArrayList;
import java.util.List;

public class IndexedModel
{
  private List<Vec4f> m_positions;
  private List<Vec4f> m_texCoords;
  private List<Vec4f> m_normals;
  private List<Vec4f> m_tangents;
  private List<Integer> m_indices;

  public IndexedModel()
  {
    m_positions = new ArrayList<Vec4f>();
    m_texCoords = new ArrayList<Vec4f>();
    m_normals = new ArrayList<Vec4f>();
    m_tangents = new ArrayList<Vec4f>();
    m_indices = new ArrayList<Vec4f>();
  }

  public void CalcNormals()
  {
    //get indices of the loaded model
    for(int i = 0; i < m_indices.size(); i+= 3)
    {
      int i0 = m_indices.get(i);
      int i1 = m_indices.get(i + 1);
      int i2 = m_indices.get(i + 2);

      Vec4f v1 = m_positions.get(i1).Sub(m_positions.get(i0));
      Vec4f v2 = m_positions.get(i2).Sub(m_positions.get(i0));

      Vec4f normal = v1.Cross(v2).Normalized();

      m_normals.set(i0, m_normals.get(i0).Add(normal));
      m_normals.set(i1, m_normals.get(i1).Add(normal));
      m_normals.set(i2, m_normals.get(i2).Add(normal));
    }

    for(int i = 0; i < m_normals.size(); i++)
      m_normals.set(i, m_normals.get(i).Normalized())

  }

  public void CalcTangents()
  {

  }

  public List<Vec4f> GetPositions() { return m_positions; }
  public List<Vec4f> GetTexCoords() { return m_texCoords; }
  public List<Vec4f> GetNormals() { return m_normals; }
  public List<Vec4f> GetTangents() { return m_tangents; }
  public List<Integer> GetIndices() { return m_indices; }

}
