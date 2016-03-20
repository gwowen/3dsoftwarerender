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
