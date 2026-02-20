# 3D Software Renderer

A simple **Java-based software rasterizer** demonstrating the fundamentals of 3D rendering entirely in software.  The project loads `.obj` models, applies perspective
projection, depth buffering and texture mapping, and allows the user to move a camera through
a 3D scene with keyboard controls.

---

## 🔧 Features

- Pure Java implementation – no native libraries or OpenGL/DirectX dependencies.
- OBJ model loader with support for textured meshes.
- Perspective projection and clipping against the view frustum.
- Triangle rasterization with depth buffering and perspective-correct texture mapping.
- Basic camera with translation/rotation, controlled via keyboard input.
- Utility classes for vectors, matrices, quaternions, transforms, and gradients.

---

## 🛠 Requirements

- Java Development Kit (JDK) 8 or newer
- GNU Make (optional – a simple `Makefile` is included)

While the code compiles using the provided Makefiles, you can also compile directly with
`javac` and run with `java` if you prefer.

---

## 🚀 Building & Running

From the repository root:

```bash
# compile everything
make

# run the sample application (moves resources temporarily)
./run.sh
```

`run.sh` simply moves the `res` directory into the classpath, starts the program and then
restores the resources directory when the application exits.  You can also run the
`Main` class manually:

```bash
# after make has been run
java -cp obj Main
```

---

## 🎮 Controls

Inside the running window use the following keys to navigate the scene:

- `W` / `S` – move forward / backward
- `A` / `D` – strafe left / right
- Arrow keys – rotate the camera around the current position (left/right = yaw, up/down = pitch)

Mouse events are captured but currently not used for camera control.

---

## 📁 Project Structure

```
Makefile               # top-level wrapper
README.md              # this file
run.sh                 # helper script to launch the program
obj/                   # compiled `.class` files
res/                   # textures and models used by the demo
src/                   # Java source files
    Bitmap.java
    Camera.java
    Display.java
    Edge.java
    Gradients.java
    IndexedModel.java
    Input.java
    Main.java
    Mat4f.java
    Mesh.java
    OBJModel.java
    Quaternion.java
    RenderContext.java
    Stars3D.java
    Transform.java
    Vec4f.java
    Vertex.java
```

Each source file implements a small piece of the renderer; the `Main` class ties
everything together in a simple animation loop.


