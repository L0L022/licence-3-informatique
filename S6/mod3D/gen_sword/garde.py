import bpy
import mathutils

longueurlame = 4
largeurlame = 3


cte = 1.0
l = 1.0
w = 1.0

a = (-w/2, -cte/2, -l/2)
b = (-w/2, cte/2, -l/2)
c = (w/2, cte/2, -l/2)
d = (w/2, -cte/2, -l/2)
e = (-w/2, -cte/2, l/2)
f = (-w/2, cte/2, l/2)
g = (w/2, cte/2, l/2)
h = (w/2, -cte/2, l/2)

sommets = [a, b, c, d, e, f, g, h]

# les sommets sont à ordonner dans le sens
# inverse des aiguilles d'une montre sur l'extérieur de la face
faces = [(0, 1, 2, 3), (7, 6, 5, 4), (5, 1, 0, 4), (6, 2, 3, 7), (4, 7, 3, 0), (5, 6, 2, 1)]

# création du mesh
mesh_data = bpy.data.meshes.new("cube_mesh_data")
mesh_data.from_pydata(sommets, [], faces)

# création de l'objet contenant le mesh, et ajout à la scène
obj = bpy.data.objects.new("My_Object", mesh_data)
bpy.context.scene.objects.link(obj)
