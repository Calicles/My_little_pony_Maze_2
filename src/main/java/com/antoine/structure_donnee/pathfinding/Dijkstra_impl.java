package com.antoine.structure_donnee.pathfinding;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;

import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * <b>Classe qui implémente l'algorithme de Dijkstra</b>
 * <p>Trouve le plus court chemin pour se rendre sur une Tile.</p>
 * fonctionne en deux vagues:
 * 1 enregistre le chemin de tuile à tuile.
 * 2 affine le path de point en point.
 *
 * @author Antoine
 */
public class Dijkstra_impl extends AbstractPathfinding_algo {

    /**
     * <p>Fonction utilisé pour trouver les Tiles adjacentes.</p>
     */
    private Predicate<Node<Tile>> byWeight;

    /**
     * <p>Fonction utilisé pour renvoyer la meilleur Tile suivante.</p>
     */
    private BinaryOperator<Node<Tile>> getMin;


    //=======   Constructeurs ==========

    public Dijkstra_impl() {
        super();

        initMethods();
    }

    public Dijkstra_impl(Rectangle entity) {
        super(entity);
    }

    //=================================

    /**
     * <p>Initialise les Méthodes des attributs : byWeight et getMin.</p>
     */
    private void initMethods() {

        byWeight = t->{

            //Distance de ce noeud au noeud courant avec ajout de la distance déja parcouru
            int dist = getDistFromCurrentNode(t);
            if (t.getParent() != null)
                dist += t.getParent().getWeight();

            //Si inusité
            if (!t.isUsed()){

                //Est adjacente
                if (getDistFromCurrentNode(t) <= Tile.getWidth()) {

                    //Poids intéressant
                    if (t.getWeight() == -1 || (t.getWeight() > 0 && t.getWeight() <= dist)) {
                        t.setWeight(dist + t.getDistFromGoal());
                        t.setParentNode(currentNode);
                        return true;
                    }
                }
            }
            return false;
        };


        getMin = (a,b)->{
                    if (a.getDistFromGoal() == 0)
                        return a;
                    else if (b.getDistFromGoal() == 0)
                        return b;
                    else
                        return (a.getWeight() < b.getWeight()) ? a : b;
                };
    }

    /**
     * <p>Boucle l'algorithme pour passer en revu tous les chemins.</p>
     * Rempli le graphe des tuiles adjacentes à la tuile courante.
     * Selectionne la meilleur comme tuile courante.
     *
     * Crée une liste chaînée en reseignant le parent de chaque noeud
     */
    @Override
    protected void startSearch() {


        //Tant qu'on a pas trouvé la fin ou qu'il y a encore des noeuds à chercher
        while (currentNode != goal && currentNode != null) {

            fillAdjGraph();

            selectNextNode();
        }
    }

    /**
     * <p>Affine le path pour produire un chemin de point en point.</p>
     * place les coordonnées du milieu de la prochaine tuile
     */
    private void createFinalPath() {// TODO Placer les coordonnées en fonction de la surface du joueur!!

        finalPath.clear();

        Tile current;

       //tant qu'il reste des tuiles
       while (!path.isEmpty()) {
           current = path.pop().getItem();

           finalPath.push(Rectangle.findMiddleCoor(current.toRectangle()));
       }


    }

    /**
     * <p>Selectionne le prochain noeud de plus petit poids dans le graphe des cases adjacentes
     * et le place en tant que noeud courant.</p>
     */
    @Override
    protected void selectNextNode() {

        Optional<Node<Tile>> res = adjNodes.stream()

                //Retourne le plus petit Poids
                .reduce(getMin);

        //On le marque comme utilisé
        if (res.isPresent()) {
            currentNode = res.get();
            currentNode.used();
        }else
            currentNode = currentNode.getParent();
    }


    /**
     * <p>Rempli le graphe des cases adjacentes à la case courante</p>
     */
    @Override
    protected void fillAdjGraph() {

        //Distance pour être adjacente
        int adjacente = currentNode.getItem().getWidth();


        //Efface le graphe de casses adjacentes au précédent Noeuds
        adjNodes.clear();

        List<Node<Tile>> buf = S.stream()

                //Filtre si le noeud est déja utilisé et si adjacent au noeud courant
                .filter(t -> byWeight.test(t))

                .collect(Collectors.toList());

        if (buf != null)
            adjNodes.addAll(buf);

    }

    /**
     * <p>Rempli la pile "path" en remontant le file de l'arrivée jusqu'au départ</p>
     * Appelle createFinalPath() pour affiner de point en point
     */
    @Override
    protected void createPath() {

        path.clear();

        Node<Tile> n = goal;

        //Tant qu'on a pas atteint la première tuile en remontant le fil depuis le but.
        //La première tuile n'est pas prise en compte, pour avoir dirèctement l'étape suivante
        while (n.getParent() != null) {
            path.addLast(n);

            n = n.getParent();
        }

        createFinalPath();
    }

    /**
     * <p>Retire de la pile la coordonées suivante.</p>
     *
     * @return la coordonnée retirée
     */
    @Override
    public Coordinates getNextStep() {
        if (finalPath.isEmpty())
            return null;
        return finalPath.pop();
    }
}
