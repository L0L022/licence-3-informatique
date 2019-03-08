import bpy
import mathutils
from math import radians

# permet de construire une liste des voisins de chaque face
def buildFaceAdjacency(obj):
    dic = {}
    for p in obj.data.polygons:
        for ek in p.edge_keys:
            if ek not in dic:
                dic[ek] = []
            dic[ek].append(p.index)
    dicF = {}
    for k in dic.keys():
        if len(dic[k]) == 2:
            if dic[k][0] not in dicF:
                dicF[dic[k][0]] = []
            if dic[k][1] not in dicF[dic[k][0]]:
                dicF[dic[k][0]].append(dic[k][1])
            if dic[k][1] not in dicF:
                dicF[dic[k][1]] = []
            if dic[k][0] not in dicF[dic[k][1]]:
                dicF[dic[k][1]].append(dic[k][0])
    return dicF

# pour rajouter des nouvelles couleurs
def newColor(col, name):
    mat = bpy.data.materials.get(name)
    if mat == None:
        mat = bpy.data.materials.new(name)
    mat.diffuse_color = col

# pour donner une couleur à une face spécifique
# ATTENTION blender doit être en mode OBJECT lors
# de l'appel pour que cette fonction marche
def setColor(obj, idFace, color):
    if color not in obj.data.materials:
        obj.data.materials.append(bpy.data.materials[color])
    obj.data.polygons[idFace].material_index = obj.data.materials.find(color)

# pour donner une couleur à tout un objet
# ATTENTION blender doit être en mode OBJECT lors
# de l'appel pour que cette fonction marche
def setColorAll(obj, color):
    if color not in obj.data.materials:
        obj.data.materials.append(bpy.data.materials[color])
    indexMat = obj.data.materials.find(color)
    for p in obj.data.polygons:
        obj.data.polygons[p.index].material_index = indexMat

# pour récupérer les faces sélectionnées
# ATTENTION blender doit être en mode OBJECT lors
# de l'appel pour que cette fonction marche
def getSelectedFacesID(obj):
    selfaces = []
    for f in obj.data.polygons:
        if f.select:
            selfaces.append(f.index)
    return selfaces

# on fait les traitement en mode OBJECT
editmode = False
if bpy.context.active_object.mode == 'EDIT':
    editmode = True
    bpy.ops.object.mode_set(mode = 'OBJECT')

# quelques nouvelles couleurs
newColor((1,1,1), "blanc")
newColor((1,0,0), "rouge")
newColor((1,1,0), "jaune")
newColor((0,1,0), "vert")
newColor((0,0,1), "bleu")
newColor((1,0,1), "violet")
newColor((0,1,1), "syan")

depth_colors = ["rouge", "violet", "bleu", "syan", "vert", "jaune"]

# on récupère notre objet
monObj = bpy.context.scene.objects['Cube']

setColorAll(monObj, "blanc")

# on récupère la liste des faces sélectionnées,
# et on les set en rouge
curSel = getSelectedFacesID(monObj)

# on construit la liste de tous les voisinages
FA = buildFaceAdjacency(monObj)

# Sélection de faces suivant une profondeur max du parcours en largeur
def selectAdjFaces(selectedFace, depth_max):
    already_colored = []
    face_to_color = [selectedFace]
    new_face = []

    depth = 0

    while depth < depth_max:
       new_face.clear()

       for v in face_to_color:
           if v in already_colored:
               continue
           already_colored.append(v)

           setColor(monObj, v, depth_colors[depth % len(depth_colors)])

           new_face.extend(FA[v])

       depth += 1
       face_to_color.clear()
       face_to_color.extend(new_face)

# Méthode 1 : Sélection de N faces
def selectNFaces(selectedFace, N):
    max_faces = N
    already_colored = []
    face_to_color = [selectedFace]
    new_face = []

    depth = 0

    while len(already_colored) < max_faces:
       new_face.clear()

       for v in face_to_color:
           if len(already_colored) >= max_faces:
               break

           if v in already_colored:
               continue
           already_colored.append(v)

           setColor(monObj, v, depth_colors[depth % len(depth_colors)])

           new_face.extend(FA[v])

       depth += 1
       face_to_color.clear()
       face_to_color.extend(new_face)

# Méthode 2 : Sélection par adjacence et par distance
def selectDistance(selectedFace, maxDistance):
    already_colored = []
    face_to_color = [selectedFace]
    new_face = []

    depth = 0
    selec_center = monObj.data.polygons[selectedFace].center

    while len(face_to_color) > 0:
       new_face.clear()

       for v in face_to_color:
           if (monObj.data.polygons[v].center - selec_center).length_squared > maxDistance:
               break

           if v in already_colored:
               continue
           already_colored.append(v)

           setColor(monObj, v, depth_colors[depth % len(depth_colors)])

           new_face.extend(FA[v])

       depth += 1
       face_to_color.clear()
       face_to_color.extend(new_face)

# Méthode 3 : Sélection par similarité de normales
def selectSimilarNormal(selectedFace, maxDiffAngle):
    already_colored = []
    face_to_color = [selectedFace]
    new_face = []

    depth = 0
    diff = radians(maxDiffAngle)

    while len(face_to_color) > 0:
        new_face.clear()

        for v in face_to_color:
            if v in already_colored:
                continue
            already_colored.append(v)

            setColor(monObj, v, depth_colors[depth % len(depth_colors)])

            n1 = monObj.data.polygons[v].normal
            for f in FA[v]:
                n2 = monObj.data.polygons[f].normal
                if n1.angle(n2) > diff:
                    continue
                new_face.append(f)

        depth += 1
        face_to_color.clear()
        face_to_color.extend(new_face)

for s in curSel:
    selectAdjFaces(s)
    # selectNFaces(s, 10)
    # selectDistance(s, 0.1)
    # selectSimilarNormal(s, 15)

# on repasse dans le mode d'origine
if editmode:
    bpy.ops.object.mode_set(mode = 'EDIT')
