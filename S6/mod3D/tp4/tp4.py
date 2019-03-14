import bpy
import mathutils
from math import radians
from math import degrees

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

# quelques nouvelles couleurs
newColor((1,1,1), "blanc")
newColor((1,0,0), "rouge")
newColor((1,1,0), "jaune")
newColor((0,1,0), "vert")
newColor((0,0,1), "bleu")
newColor((1,0,1), "violet")
newColor((0,1,1), "syan")

# on récupère notre objet
monObj = bpy.context.scene.objects['Cube']

setColorAll(monObj, "blanc")

# on construit la liste de tous les voisinages
FA = buildFaceAdjacency(monObj)

def p1():
    maxAngle = radians(15)

    for f in monObj.data.polygons:
        sum = 0
        div = 0
        for f_adj in FA[f.index]:
            f_adj = monObj.data.polygons[f_adj]
            sum += f.normal.angle(f_adj.normal) * f_adj.area
            div += f_adj.area
        angle = sum / div
        setColor(monObj, f.index, "blanc" if angle < maxAngle else "violet")


def p2():
    maxAngle = 2

    for f in monObj.data.polygons:
        sum = 0
        div = 0
        for f_adj in FA[f.index]:
            f_adj = monObj.data.polygons[f_adj]
            sign = 1 if mathutils.geometry.distance_point_to_plane(f_adj.center, f.center, f.normal) <= 0 else -1
            sum += f.normal.angle(f_adj.normal) * f_adj.area * sign
            div += f_adj.area
        angle = sum / div
        angle = degrees(angle)
        
        if abs(angle) < maxAngle:
            color = "blanc"
        elif angle > 0:
            color = "rouge"
        else:
            color = "bleu"
        
        setColor(monObj, f.index, color)

def p3():
    maxAngle = 2

    for f in monObj.data.polygons:
        sum = 0
        div = 0
        nb_pos = 0
        nb_neg = 0
        for f_adj in FA[f.index]:
            f_adj = monObj.data.polygons[f_adj]
            sign = 1 if mathutils.geometry.distance_point_to_plane(f_adj.center, f.center, f.normal) <= 0 else -1
            sum += f.normal.angle(f_adj.normal) * f_adj.area * sign
            div += f_adj.area
            
            if sign >= 0:
                nb_pos += 1
            else:
                nb_neg += 1

        angle = sum / div
        angle = degrees(angle)
        
        if abs(angle) < maxAngle:
            color = "blanc"
        elif angle >= 0:
            if nb_neg == 0:
                color = "rouge"
            else:
                color = "jaune"
        else:
            if nb_pos == 0:
                color = "bleu"
            else:
                color = "syan"
        
        setColor(monObj, f.index, color)


p3()
