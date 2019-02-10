import bpy
import mathutils

longueurlame = 4
largeurlame = 3
c1 = 0.5
c2 = 1
c3 = 1
c4 = 1

a =  mathutils.Vector((-largeurlame/2, 0, 0))
b =  mathutils.Vector((-c2, -c3, 0))
c =  mathutils.Vector((0, -c1, 0))
d =  mathutils.Vector((c2, -c3, 0))
e =  mathutils.Vector((largeurlame/2, 0, 0))

b_p =  mathutils.Vector((-c2, c3, 0))
c_p =  mathutils.Vector((0, c1, 0))
d_p =  mathutils.Vector((c2, c3, 0))

up =  mathutils.Vector((0, 0, longueurlame))

f = a + up
g = b + up
h = c + up
i = d + up
j = e + up

g_p = b_p + up
h_p = c_p + up
i_p = d_p + up

k = up +  mathutils.Vector((0, 0, c4))


sommets = [a, b, c, d, e, f, g, h, i, j, k, b_p, c_p, d_p, g_p, h_p, i_p]

# les sommets sont à ordonner dans le sens
# inverse des aiguilles d'une montre sur l'extérieur de la face
faces = [(0, 1, 6, 5), (1, 2, 7, 6), (2, 3, 8, 7), (3, 4, 9, 8), (5, 6, 10), (6, 7, 10), (7, 8, 10), (8, 9, 10), (11, 0, 5, 14), (12, 11, 14, 15), (13, 12, 15, 16), (4, 13, 16, 9), (14, 5, 10), (15, 14, 10), (16, 15, 10), (9, 16, 10)]

# création du mesh
mesh_data = bpy.data.meshes.new("cube_mesh_data")
mesh_data.from_pydata(sommets, [], faces)

# création de l'objet contenant le mesh, et ajout à la scène
obj = bpy.data.objects.new("My_Object", mesh_data)
bpy.context.scene.objects.link(obj)
