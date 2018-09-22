package Exercice1;

import java.util.Arrays;

/** * La classe Vector implémente un tableau d'entiers * de taille dynamique. Les éléments du vecteur sont stockés dans un tableau. * La taille de ce tableau est au minimum doublée à chaque fois qu'il est * nécessaire de le faire grossir. */
public class Vector {

    /**
     * Tableau permettant de stocker les éléments du vecteur.
     * Seuls les size premiers éléments font partie du vecteur.
     * La taille de ce tableau est égale à la capacité du vecteur, c'est-à-dire,
     * au nombre d'éléments maximum que le vecteur peut contenir sans
     * avoir besoin d'allouer un nouveau tableau.
     */
    private int[] elements;

    /**
     * Nombre d'éléments présents dans le vecteur.
     */
    private int size;

    /**
     * Construit un vecteur de capacité initiale initialCapacity.
     *
     * @param initialCapacity Capacité initiale du vecteur
     */
    public Vector(int initialCapacity) {
        elements = new int[initialCapacity];
        size = 0;
    }

    /**
     * Construit un vecteur avec une capacité initiale de 10.
     */
    public Vector() {
        this(10);
    }

    /**
     * Augmente la capacité du vecteur si nécessaire de façon
     * à permettre le stockage d'au moins <code>minCapacity</code>
     * éléments. S'il est nécessaire d'augmenter la capacité du vecteur,
     * elle est au minimum doublée.
     *
     * @param minCapacity Capacité minimale à assurer
     */
    public void ensureCapacity(int minCapacity) {
    	if (minCapacity < 0) return;
        int oldCapacity = elements.length;
        if (oldCapacity >= minCapacity) return;
        int newCapacity = Math.max(oldCapacity * 2, minCapacity);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Change la taille du vecteur.
     * Si la nouvelle taille est plus petite que l'encienne alors le vecteur est réduit.
     * Si la nouvelle taille est plus grande alors les nouveaux éléments sont initialisés à zéro.
     * 
     * @param newSize La nouvelle taille du vecteur.
     */
    public void resize(int newSize) {
    	if (newSize < 0) return;
    	
        ensureCapacity(newSize);
        if (newSize > size) {
        	Arrays.fill(elements, size, newSize, 0);
        }
        this.size = newSize;
    }

    /**
     * Retourne la capacité du vecteur.
     *
     * @return Capacité du vecteur.
     */
    public int capacity() {
        return elements.length;
    }

    /**
     * Retourne le nombre d'éléments dans le vecteur.
     * 
     * @return Le nombre d'éléments dans le vecteur.
     */
    public int size() {
    	return this.size;
    }
    
    /**
     * Indique si le vecteur n'a pas d'éléments.
     * 
     * @return Vrai si le vecteur est vide sinon faux.
     */
    public boolean isEmpty() {
    	return size() == 0;
    }
    
    /**
     * Ajoute un élément à la fin du vecteur.
     * 
     * @param element L'élément ajouté
     */
    public void add(int element) {
    	ensureCapacity(size + 1);
    	elements[size] = element;
    	size += 1;
    }
    
    /**
     * Met l'élément element à l'indice index dans le vecteur.
     * Ne fait rien si index n'est pas valide.
     * 
     * @param index L'indice de l'emplacement de l'élément
     * @param element L'élément a placer
     */
    public void set(int index, int element) {
    	if (!validIndex(index)) return;
    	elements[index] = element;
    }
    
    /**
     * Renvoie l'élément stocké à l'indice index donné.
     * Si index n'est pas valide retourne zéro.
     * 
     * @param index L'indice de l'emplacement de l'élément
     * @return L'élément à l'emplacement index
     */
    public int get(int index) {
    	if (!validIndex(index)) return 0;
    	return elements[index];
    }
    
    private boolean validIndex(int index) {
    	return 0 <= index && index < size();
    }
}


