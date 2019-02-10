import bpy
import mathutils

longueurmanche = 4
taillepommeau = 0.5

bpy.ops.mesh.primitive_uv_sphere_add(size=taillepommeau, location=(0, 0, -(longueurmanche + taillepommeau / 2)))
