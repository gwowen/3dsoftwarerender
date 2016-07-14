import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class Mesh
{
  private List<Vertex> m_vertices;
  private List<Integer> m_indices;

  // Load mesh
  public Mesh(String fileName) throws IOException
  {
    // convert the loaded OBJ to the internal IndexedModel format
    IndexedModel model = new OBJModel(fileName).ToIndexedModel();

    m_vertices = new ArrayList<Vertex>();
    // add vertices from model to list
    for(int i = 0; i < model.GetPositions().size(); i++)
    {
      m_vertices.add(new Vertex(model.GetPositions().get(i),
                                model.GetTexCoords().get(i)),
                                model.GetNormals().get(i));
    }

    m_indices = model.GetIndices();
  }

  public void Draw(RenderContext context, Mat4f transform, Bitmap texture)
  {
    Matrix4f mvp = viewProjection.Mul(transform);
    for(int i = 0; i < m_indices.size(); i += 3)
    {
      context.DrawTriangle(
        m_vertices.get(m_indices.get(i)).Transform(transform),
        m_vertices.get(m_indices.get(i + 1)).Transform(transform),
        m_vertices.get(m_indices.get(i + 2)).Transform(transform),
        texture);
    }
  }
}
