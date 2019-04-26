#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <OpenMesh/Core/Mesh/PolyConnectivity.hh>

#include <unordered_set>
#include <boost/heap/fibonacci_heap.hpp>
#include <limits>

/* **** début de la partie à compléter **** */

void MainWindow::_showSelections(MyMesh* _mesh) {
    if (vertexSelection >= 0) {
        VertexHandle vh = _mesh->vertex_handle(static_cast<unsigned int>(vertexSelection));
        _mesh->set_color(vh, MyMesh::Color(255, 0, 0));
        _mesh->data(vh).thickness = 12;
    }

    if (edgeSelection >= 0) {
        EdgeHandle eh = _mesh->edge_handle(static_cast<unsigned int>(edgeSelection));
        HalfedgeHandle heh = _mesh->halfedge_handle(eh, 0);
        VertexHandle vh_1 = _mesh->to_vertex_handle(heh);
        VertexHandle vh_2 = _mesh->from_vertex_handle(heh);
        MyMesh::Color color(0, 255, 0);

        _mesh->set_color(eh, color);
        _mesh->data(eh).thickness = 4;

        _mesh->set_color(vh_1, color);
        _mesh->data(vh_1).thickness = 12;

        _mesh->set_color(vh_2, color);
        _mesh->data(vh_2).thickness = 12;
    }

    if (faceSelection >= 0) {
        FaceHandle fh = _mesh->face_handle(static_cast<unsigned int>(faceSelection));
        MyMesh::Color color(0, 0, 255);

        _mesh->set_color(fh, color);

        HalfedgeHandle first_heh = _mesh->halfedge_handle(fh);
        HalfedgeHandle current_heh = first_heh;
        do {
            EdgeHandle eh = _mesh->edge_handle(current_heh);
            VertexHandle vh = _mesh->to_vertex_handle(current_heh);

            _mesh->set_color(eh, color);
            _mesh->data(eh).thickness = 4;

            _mesh->set_color(vh, color);
            _mesh->data(vh).thickness = 12;

            current_heh = _mesh->next_halfedge_handle(current_heh);
        } while (current_heh != first_heh);
    }
}

void MainWindow::showSelections(MyMesh* _mesh)
{
    // on réinitialise les couleurs de tout le maillage
    resetAllColorsAndThickness(_mesh);

    /* **** à compléter ! ****
     * cette fonction utilise les vatiables de sélection vertexSelection, edgeSelection et faceSelection
     * qui sont les ID des élements sélectionnés et qui sont égales à -1 si la sélection est vide
     */

    _showSelections(_mesh);

    // on affiche le nouveau maillage
    displayMesh(_mesh);
}


void MainWindow::showSelectionsNeighborhood(MyMesh* _mesh)
{
    // on réinitialise les couleurs de tout le maillage
    resetAllColorsAndThickness(_mesh);

    /* **** à compléter ! ****
     * cette fonction utilise les vatiables de sélection vertexSelection, edgeSelection et faceSelection
     * qui sont les ID des élements sélectionnés et qui sont égales à -1 si la sélection est vide
     * et affiche en plus le voisinage de chaque sélection :
     *    - les faces voisines les faces
     *    - les faces adjacentes pour les arêtes
     *    - les arêtes incidentes pour les sommets
     */

    _showSelections(_mesh);

    if (vertexSelection >= 0) {
        VertexHandle vh = _mesh->vertex_handle(static_cast<unsigned int>(vertexSelection));

        for (auto it = _mesh->ve_iter(vh); it.is_valid(); ++it) {
            EdgeHandle eh = *it;
            _mesh->set_color(eh, MyMesh::Color(127, 0, 0));
            _mesh->data(eh).thickness = 12;
        }
    }

    if (edgeSelection >= 0) {
        EdgeHandle eh = _mesh->edge_handle(static_cast<unsigned int>(edgeSelection));
        for (HalfedgeHandle heh : {_mesh->halfedge_handle(eh, 0), _mesh->halfedge_handle(eh, 1)}) {
            FaceHandle fh = _mesh->face_handle(heh);
            _mesh->set_color(fh, MyMesh::Color(0, 127, 0));
        }
    }

    if (faceSelection >= 0) {
        FaceHandle fh = _mesh->face_handle(static_cast<unsigned int>(faceSelection));
        for (auto it = _mesh->ff_iter(fh); it.is_valid(); ++it) {
            FaceHandle fh = *it;
            _mesh->set_color(fh, MyMesh::Color(0, 0, 127));
        }
    }

    // on affiche le nouveau maillage
    displayMesh(_mesh);
}



void MainWindow::showBorder(MyMesh* _mesh)
{
    // on réinitialise l'affichage
    resetAllColorsAndThickness(_mesh);

    /* **** à compléter ! **** */
    for (auto it = _mesh->edges_begin(); it != _mesh->edges_end(); ++it) {
        EdgeHandle eh = *it;
        if (!_mesh->is_boundary(eh))
            continue;

        _mesh->set_color(eh, MyMesh::Color(200, 0, 200));
        _mesh->data(eh).thickness = 4;
    }

    // on affiche le nouveau maillage
    displayMesh(_mesh);
}

void MainWindow::showPath(MyMesh* _mesh, int v1, int v2)
{
    // on réinitialise l'affichage
    resetAllColorsAndThickness(_mesh);

    /* **** à compléter ! **** */

    // ____ parcourt en largeur totalement banal ____
    /*
    for (auto it = _mesh->vertices_begin(); it != _mesh->vertices_end(); ++it) {
        VertexHandle vh = *it;
        _mesh->data(vh).is_visited = false;
        _mesh->data(vh).from_heh.invalidate();
    }

    std::vector<VertexHandle> vh_to_visit = {_mesh->vertex_handle(static_cast<unsigned int>(v1))};
    std::vector<VertexHandle> futur_vh_to_visit;
    while (!vh_to_visit.empty()) {
        for (VertexHandle vh : vh_to_visit) {
            if (vh == _mesh->vertex_handle(static_cast<unsigned int>(v2))) {
                futur_vh_to_visit.clear();
                break;
            }

            _mesh->set_color(vh, MyMesh::Color(200, 0, 0));
            _mesh->data(vh).thickness = 12;

            for (auto it = _mesh->voh_iter(vh); it.is_valid(); ++it) {
                HalfedgeHandle heh = *it;
                VertexHandle vh = _mesh->to_vertex_handle(heh);

                bool &is_visited = _mesh->data(vh).is_visited;
                if (is_visited)
                    continue;
                is_visited = true;

                _mesh->data(vh).from_heh = heh;
                futur_vh_to_visit.push_back(vh);
            }
        }
        vh_to_visit = futur_vh_to_visit;
        futur_vh_to_visit.clear();
    }
    */

    // ____ dijkstra ____
    for (auto it = _mesh->vertices_begin(); it != _mesh->vertices_end(); ++it) {
        VertexHandle vh = *it;
        _mesh->data(vh).is_visited = false;
        _mesh->data(vh).from_heh.invalidate();
        _mesh->data(vh).minimal_distance = std::numeric_limits<float>::max();
        _mesh->data(vh).handle = nullptr;
    }

    std::unordered_set<VertexHandle> vh_visited;

    auto cmp = [_mesh](const VertexHandle &a, const VertexHandle &b) {
        return _mesh->data(a).minimal_distance > _mesh->data(b).minimal_distance;
    };

    boost::heap::fibonacci_heap<VertexHandle, boost::heap::compare<decltype (cmp)>> vh_discovered(cmp);
    vh_discovered.push(_mesh->vertex_handle(static_cast<unsigned int>(v1)));

    typedef typename boost::heap::fibonacci_heap<VertexHandle, boost::heap::compare<decltype (cmp)>>::handle_type handle_t;
    std::vector<handle_t> handles(_mesh->n_vertices());

    _mesh->data(_mesh->vertex_handle(static_cast<unsigned int>(v1))).minimal_distance = 0;

    while(vh_visited.find(_mesh->vertex_handle(static_cast<unsigned int>(v2))) == vh_visited.end()) {
        VertexHandle vh = vh_discovered.top();
        vh_discovered.pop();
        vh_visited.insert(vh);

        _mesh->set_color(vh, MyMesh::Color(200, 0, 0));
        _mesh->data(vh).thickness = 8;

        for (auto it = _mesh->voh_iter(vh); it.is_valid(); ++it) {
            HalfedgeHandle heh = *it;
            VertexHandle vh_to = _mesh->to_vertex_handle(heh);

            float distance = _mesh->data(vh).minimal_distance + (_mesh->point(vh_to) - _mesh->point(vh)).length();

            if (_mesh->data(vh_to).is_visited) {
                if (distance < _mesh->data(vh_to).minimal_distance) {
                    _mesh->data(vh_to).from_heh = heh;
                    _mesh->data(vh_to).minimal_distance = distance;
                    vh_discovered.update(*static_cast<handle_t *>(_mesh->data(vh_to).handle));
                }
            } else {
                _mesh->data(vh_to).is_visited = true;
                _mesh->data(vh_to).from_heh = heh;
                _mesh->data(vh_to).minimal_distance = distance;

                auto h = vh_discovered.push(vh_to);
                handles.push_back(h);
                _mesh->data(vh_to).handle = std::addressof(handles.back());
            }
        }
    }

    VertexHandle ve_begin = _mesh->vertex_handle(static_cast<unsigned int>(v2));
    VertexHandle ve_end = _mesh->vertex_handle(static_cast<unsigned int>(v1));
    for (auto ve = ve_begin; ve != ve_end; ve = _mesh->from_vertex_handle(_mesh->data(ve).from_heh)) {
        EdgeHandle eh = _mesh->edge_handle(_mesh->data(ve).from_heh);
        _mesh->set_color(eh, MyMesh::Color(0, 100, 0));
        _mesh->data(eh).thickness = 15;
    }

    // point de départ et point d'arrivée en vert et en gros
    _mesh->set_color(_mesh->vertex_handle(v1), MyMesh::Color(0, 255, 0));
    _mesh->set_color(_mesh->vertex_handle(v2), MyMesh::Color(0, 255, 0));
    _mesh->data(_mesh->vertex_handle(v1)).thickness = 20;
    _mesh->data(_mesh->vertex_handle(v2)).thickness = 20;

    // on affiche le nouveau maillage
    displayMesh(_mesh);
}

/* **** fin de la partie à compléter **** */


/* **** début de la partie boutons et IHM **** */

void MainWindow::on_pushButton_bordure_clicked()
{
    showBorder(&mesh);
}

void MainWindow::on_pushButton_voisinage_clicked()
{
    // changement de mode entre avec et sans voisinage
    if(modevoisinage)
    {
        ui->pushButton_voisinage->setText("Repasser en mode normal");
        modevoisinage = false;
    }
    else
    {
        ui->pushButton_voisinage->setText("Passer en mode voisinage");
        modevoisinage = true;
    }

    // on montre la nouvelle selection
    if(!modevoisinage)
        showSelections(&mesh);
    else
        showSelectionsNeighborhood(&mesh);
}


void MainWindow::on_pushButton_vertexMoins_clicked()
{
    // mise à jour de l'interface
    vertexSelection = vertexSelection - 1;
    ui->labelVertex->setText(QString::number(vertexSelection));

    // on montre la nouvelle selection
    if(!modevoisinage)
        showSelections(&mesh);
    else
        showSelectionsNeighborhood(&mesh);
}

void MainWindow::on_pushButton_vertexPlus_clicked()
{
    // mise à jour de l'interface
    vertexSelection = vertexSelection + 1;
    ui->labelVertex->setText(QString::number(vertexSelection));

    // on montre la nouvelle selection
    if(!modevoisinage)
        showSelections(&mesh);
    else
        showSelectionsNeighborhood(&mesh);
}

void MainWindow::on_pushButton_edgeMoins_clicked()
{
    // mise à jour de l'interface
    edgeSelection = edgeSelection - 1;
    ui->labelEdge->setText(QString::number(edgeSelection));

    // on montre la nouvelle selection
    if(!modevoisinage)
        showSelections(&mesh);
    else
        showSelectionsNeighborhood(&mesh);
}

void MainWindow::on_pushButton_edgePlus_clicked()
{
    // mise à jour de l'interface
    edgeSelection = edgeSelection + 1;
    ui->labelEdge->setText(QString::number(edgeSelection));

    // on montre la nouvelle selection
    if(!modevoisinage)
        showSelections(&mesh);
    else
        showSelectionsNeighborhood(&mesh);
}

void MainWindow::on_pushButton_faceMoins_clicked()
{
    // mise à jour de l'interface
    faceSelection = faceSelection - 1;
    ui->labelFace->setText(QString::number(faceSelection));

    // on montre la nouvelle selection
    if(!modevoisinage)
        showSelections(&mesh);
    else
        showSelectionsNeighborhood(&mesh);
}

void MainWindow::on_pushButton_facePlus_clicked()
{
    // mise à jour de l'interface
    faceSelection = faceSelection + 1;
    ui->labelFace->setText(QString::number(faceSelection));

    // on montre la nouvelle selection
    if(!modevoisinage)
        showSelections(&mesh);
    else
        showSelectionsNeighborhood(&mesh);
}

void MainWindow::on_pushButton_afficherChemin_clicked()
{
    // on récupère les sommets de départ et d'arrivée
    int indexV1 = ui->spinBox_v1_chemin->value();
    int indexV2 = ui->spinBox_v2_chemin->value();

    showPath(&mesh, indexV1, indexV2);
}


void MainWindow::on_pushButton_chargement_clicked()
{
    // fenêtre de sélection des fichiers
    QString fileName = QFileDialog::getOpenFileName(this, tr("Open Mesh"), "", tr("Mesh Files (*.obj)"));

    // chargement du fichier .obj dans la variable globale "mesh"
    OpenMesh::IO::read_mesh(mesh, fileName.toUtf8().constData());

    // initialisation des couleurs et épaisseurs (sommets et arêtes) du mesh
    resetAllColorsAndThickness(&mesh);

    // on affiche le maillage
    displayMesh(&mesh);

    ui->spinBox_v1_chemin->setMaximum(mesh.n_vertices());
    ui->spinBox_v2_chemin->setMaximum(mesh.n_vertices());
}

/* **** fin de la partie boutons et IHM **** */



/* **** fonctions supplémentaires **** */

// permet d'initialiser les couleurs et les épaisseurs des élements du maillage
void MainWindow::resetAllColorsAndThickness(MyMesh* _mesh)
{
    for (MyMesh::VertexIter curVert = _mesh->vertices_begin(); curVert != _mesh->vertices_end(); curVert++)
    {
        _mesh->data(*curVert).thickness = 1;
        _mesh->set_color(*curVert, MyMesh::Color(0, 0, 0));
    }

    for (MyMesh::FaceIter curFace = _mesh->faces_begin(); curFace != _mesh->faces_end(); curFace++)
    {
        _mesh->set_color(*curFace, MyMesh::Color(150, 150, 150));
    }

    for (MyMesh::EdgeIter curEdge = _mesh->edges_begin(); curEdge != _mesh->edges_end(); curEdge++)
    {
        _mesh->data(*curEdge).thickness = 1;
        _mesh->set_color(*curEdge, MyMesh::Color(0, 0, 0));
    }
}

// charge un objet MyMesh dans l'environnement OpenGL
void MainWindow::displayMesh(MyMesh* _mesh)
{
    GLuint* triIndiceArray = new GLuint[_mesh->n_faces() * 3];
    GLfloat* triCols = new GLfloat[_mesh->n_faces() * 3 * 3];
    GLfloat* triVerts = new GLfloat[_mesh->n_faces() * 3 * 3];

    MyMesh::ConstFaceIter fIt(_mesh->faces_begin()), fEnd(_mesh->faces_end());
    MyMesh::ConstFaceVertexIter fvIt;
    int i = 0;
    for (; fIt!=fEnd; ++fIt)
    {
        fvIt = _mesh->cfv_iter(*fIt);
        triCols[3*i+0] = _mesh->color(*fIt)[0]; triCols[3*i+1] = _mesh->color(*fIt)[1]; triCols[3*i+2] = _mesh->color(*fIt)[2];
        triVerts[3*i+0] = _mesh->point(*fvIt)[0]; triVerts[3*i+1] = _mesh->point(*fvIt)[1]; triVerts[3*i+2] = _mesh->point(*fvIt)[2];
        triIndiceArray[i] = i;

        i++; ++fvIt;
        triCols[3*i+0] = _mesh->color(*fIt)[0]; triCols[3*i+1] = _mesh->color(*fIt)[1]; triCols[3*i+2] = _mesh->color(*fIt)[2];
        triVerts[3*i+0] = _mesh->point(*fvIt)[0]; triVerts[3*i+1] = _mesh->point(*fvIt)[1]; triVerts[3*i+2] = _mesh->point(*fvIt)[2];
        triIndiceArray[i] = i;

        i++; ++fvIt;
        triCols[3*i+0] = _mesh->color(*fIt)[0]; triCols[3*i+1] = _mesh->color(*fIt)[1]; triCols[3*i+2] = _mesh->color(*fIt)[2];
        triVerts[3*i+0] = _mesh->point(*fvIt)[0]; triVerts[3*i+1] = _mesh->point(*fvIt)[1]; triVerts[3*i+2] = _mesh->point(*fvIt)[2];
        triIndiceArray[i] = i;

        i++;
    }

    ui->displayWidget->loadMesh(triVerts, triCols, _mesh->n_faces() * 3 * 3, triIndiceArray, _mesh->n_faces() * 3);

    delete[] triIndiceArray;
    delete[] triCols;
    delete[] triVerts;

    GLuint* linesIndiceArray = new GLuint[_mesh->n_edges() * 2];
    GLfloat* linesCols = new GLfloat[_mesh->n_edges() * 2 * 3];
    GLfloat* linesVerts = new GLfloat[_mesh->n_edges() * 2 * 3];

    i = 0;
    QHash<float, QList<int> > edgesIDbyThickness;
    for (MyMesh::EdgeIter eit = _mesh->edges_begin(); eit != _mesh->edges_end(); ++eit)
    {
        float t = _mesh->data(*eit).thickness;
        if(t > 0)
        {
            if(!edgesIDbyThickness.contains(t))
                edgesIDbyThickness[t] = QList<int>();
            edgesIDbyThickness[t].append((*eit).idx());
        }
    }
    QHashIterator<float, QList<int> > it(edgesIDbyThickness);
    QList<QPair<float, int> > edgeSizes;
    while (it.hasNext())
    {
        it.next();

        for(int e = 0; e < it.value().size(); e++)
        {
            int eidx = it.value().at(e);

            MyMesh::VertexHandle vh1 = _mesh->to_vertex_handle(_mesh->halfedge_handle(_mesh->edge_handle(eidx), 0));
            linesVerts[3*i+0] = _mesh->point(vh1)[0];
            linesVerts[3*i+1] = _mesh->point(vh1)[1];
            linesVerts[3*i+2] = _mesh->point(vh1)[2];
            linesCols[3*i+0] = _mesh->color(_mesh->edge_handle(eidx))[0];
            linesCols[3*i+1] = _mesh->color(_mesh->edge_handle(eidx))[1];
            linesCols[3*i+2] = _mesh->color(_mesh->edge_handle(eidx))[2];
            linesIndiceArray[i] = i;
            i++;

            MyMesh::VertexHandle vh2 = _mesh->from_vertex_handle(_mesh->halfedge_handle(_mesh->edge_handle(eidx), 0));
            linesVerts[3*i+0] = _mesh->point(vh2)[0];
            linesVerts[3*i+1] = _mesh->point(vh2)[1];
            linesVerts[3*i+2] = _mesh->point(vh2)[2];
            linesCols[3*i+0] = _mesh->color(_mesh->edge_handle(eidx))[0];
            linesCols[3*i+1] = _mesh->color(_mesh->edge_handle(eidx))[1];
            linesCols[3*i+2] = _mesh->color(_mesh->edge_handle(eidx))[2];
            linesIndiceArray[i] = i;
            i++;
        }
        edgeSizes.append(qMakePair(it.key(), it.value().size()));
    }

    ui->displayWidget->loadLines(linesVerts, linesCols, i * 3, linesIndiceArray, i, edgeSizes);

    delete[] linesIndiceArray;
    delete[] linesCols;
    delete[] linesVerts;

    GLuint* pointsIndiceArray = new GLuint[_mesh->n_vertices()];
    GLfloat* pointsCols = new GLfloat[_mesh->n_vertices() * 3];
    GLfloat* pointsVerts = new GLfloat[_mesh->n_vertices() * 3];

    i = 0;
    QHash<float, QList<int> > vertsIDbyThickness;
    for (MyMesh::VertexIter vit = _mesh->vertices_begin(); vit != _mesh->vertices_end(); ++vit)
    {
        float t = _mesh->data(*vit).thickness;
        if(t > 0)
        {
            if(!vertsIDbyThickness.contains(t))
                vertsIDbyThickness[t] = QList<int>();
            vertsIDbyThickness[t].append((*vit).idx());
        }
    }
    QHashIterator<float, QList<int> > vitt(vertsIDbyThickness);
    QList<QPair<float, int> > vertsSizes;

    while (vitt.hasNext())
    {
        vitt.next();

        for(int v = 0; v < vitt.value().size(); v++)
        {
            int vidx = vitt.value().at(v);

            pointsVerts[3*i+0] = _mesh->point(_mesh->vertex_handle(vidx))[0];
            pointsVerts[3*i+1] = _mesh->point(_mesh->vertex_handle(vidx))[1];
            pointsVerts[3*i+2] = _mesh->point(_mesh->vertex_handle(vidx))[2];
            pointsCols[3*i+0] = _mesh->color(_mesh->vertex_handle(vidx))[0];
            pointsCols[3*i+1] = _mesh->color(_mesh->vertex_handle(vidx))[1];
            pointsCols[3*i+2] = _mesh->color(_mesh->vertex_handle(vidx))[2];
            pointsIndiceArray[i] = i;
            i++;
        }
        vertsSizes.append(qMakePair(vitt.key(), vitt.value().size()));
    }

    ui->displayWidget->loadPoints(pointsVerts, pointsCols, i * 3, pointsIndiceArray, i, vertsSizes);

    delete[] pointsIndiceArray;
    delete[] pointsCols;
    delete[] pointsVerts;
}


MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow)
{
    vertexSelection = -1;
    edgeSelection = -1;
    faceSelection = -1;

    modevoisinage = false;

    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

