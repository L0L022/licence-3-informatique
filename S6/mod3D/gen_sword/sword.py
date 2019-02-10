import bpy
import mathutils

import bpy

class EpeeFactory:

    def __init__(self, name, longueurlame, largeurlame, longueurmanche, taillepommeau, taillegarde):
        self.name = name
        self.longueurlame = longueurlame
        self.largeurlame = largeurlame
        self.profondeurlame = self.largeurlame / 4 * 0.3
        self.longueurmanche = longueurmanche
        self.taillepommeau = taillepommeau
        self.taillegarde = taillegarde
        self.parts = []

    def new(self):
        self._genLame()
        self._genGarde()
        self._genPommeau()
        self._genManche()
        self._mergeParts()

        obj = bpy.context.selected_objects[0]

        obj.name = self.name
        return obj

    # fonction pour générer un maillage et l'ajouter à la scène
    def _buildMesh(self, sommets, faces, nom):
        # création du mesh
        mesh_data = bpy.data.meshes.new(nom)
        mesh_data.from_pydata(sommets, [], faces)
        # création de l'objet contenant le mesh, et ajout à la scène
        obj = bpy.data.objects.new("My_Object", mesh_data)
        bpy.context.scene.objects.link(obj)
        self.parts.append(obj)

    def _addSelectedObjectToParts(self):
        self.parts += bpy.context.selected_objects

    def _mergeParts(self):
        for p in self.parts:
            p.select = True
        bpy.ops.object.join()

    def _genLame(self):
        # variables documentées sur ma feuille de brouillon
        c1 = 0.5 * self.profondeurlame
        c2 = self.profondeurlame
        c3 = self.profondeurlame
        c4 = self.longueurlame * 0.1 # longueur bout de l'épée

        a =  mathutils.Vector((-self.largeurlame/2, 0, 0))
        b =  mathutils.Vector((-c2, -c3, 0))
        c =  mathutils.Vector((0, -c1, 0))
        d =  mathutils.Vector((c2, -c3, 0))
        e =  mathutils.Vector((self.largeurlame/2, 0, 0))

        b_p =  mathutils.Vector((-c2, c3, 0))
        c_p =  mathutils.Vector((0, c1, 0))
        d_p =  mathutils.Vector((c2, c3, 0))

        up =  mathutils.Vector((0, 0, self.longueurlame))

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

        faces = [(0, 1, 6, 5), (1, 2, 7, 6), (2, 3, 8, 7), (3, 4, 9, 8), (5, 6, 10), (6, 7, 10), (7, 8, 10), (8, 9, 10), (11, 0, 5, 14), (12, 11, 14, 15), (13, 12, 15, 16), (4, 13, 16, 9), (14, 5, 10), (15, 14, 10), (16, 15, 10), (9, 16, 10)]
        self._buildMesh(sommets, faces, "lame")

    def _genPommeau(self):
        bpy.ops.mesh.primitive_uv_sphere_add(size=self.taillepommeau, location=(0, 0, -(self.longueurmanche + self.taillepommeau / 2)))
        self._addSelectedObjectToParts()

    def _genGarde(self):
        cte = max(self.profondeurlame, 0.1) * 2.1
        l = 0.41
        w = self.taillegarde

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
        self._buildMesh(sommets, faces, "garde")

    def _genManche(self):
        bpy.ops.mesh.primitive_cylinder_add(radius=0.1, depth=self.longueurmanche, location=(0.0, 0.0, -self.longueurmanche/2))
        self._addSelectedObjectToParts()

# fonction pour supprimer de la scène tous les éléments
def cleanAll():
    bpy.ops.object.select_all(action='SELECT')
    bpy.ops.object.delete(use_global=False)

# notre fonction pour générer des épées
def genererEpee(name, longueurlame, largeurlame, longueurmanche, taillepommeau, taillegarde, location):
    ef = EpeeFactory(name, longueurlame, largeurlame, longueurmanche, taillepommeau, taillegarde)
    e = ef.new()
    e.location = location

# ***** Programme principal *****
cleanAll()
#genererEpee("Épée", 10, 0.5, 2, 0.2, 1, (0, 0, 0))
#Épée 000 
genererEpee("Épée", 1, 1, 6, 0.3, 1.4, (10, 0, 0))
#Épée 001
genererEpee("Épée", 7, 0.5, 2, 0.2, 2.5, (20, 0, 0))
#Épée 002
genererEpee("Épée", 7, 0.3, 2, 0.2, 0.8, (30, 0, 0))
#Épée 003
genererEpee("Épée", 2, 0.3, 1, 0.2, 0.8, (40, 0, 0))
#Épée 004
genererEpee("Épée", 10, 3, 2, 0.4, 3.5, (50, 0, 0))
