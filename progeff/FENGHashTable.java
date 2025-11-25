package hash;

import java.security.KeyStore.Entry;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic hash table implementation, mapping keys to values.
 * 
 * @param <K>
 *            the type of the keys
 * @param <V>
 *            the type of the values
 */
public class FENGHashTable<K, V> implements Map<K, V>, Iterable<Mapping<K, V>>{

	protected List<Mapping<K, V>>[] table;

	protected int size;

    /**
     * Construct an empty hashtable with an initial capacity of 4.
     */
    public FENGHashTable() {
        size = 0;
        table = (List<Mapping<K, V>>[]) new List[4];
        for (int i = 0; i < 4; i++) {
            table[i] = new ArrayList<Mapping<K, V>>();
        }
    }

	/**
	 * Associate the specified value with the specified key in this map. If the
	 * map previously contained a mapping for the key, the old value is
	 * replaced.
	 * 
	 * @param cle
	 *            key with which the specified value is to be associated
	 * @param valeur
	 *            value to be associated with the specified key
	 * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
	 *         if there was no mapping for <tt>key</tt>.
	 * @throws NullPointerException
	 *             if the key or the value is <tt>null</tt>
	 */
    public V put(K cle, V valeur) {
        if (cle == null || valeur == null) throw new NullPointerException();
        if ((size + 1) * 100 / table.length >= 80) {
            agrandir();
        }

        int indice = Math.floorMod(cle.hashCode(), table.length);
        for (Mapping<K, V> truc : table[indice]) {
            if (truc.getKey().equals(cle)) {
                V ancien = truc.getValue();
                truc.setValue(valeur);
                return ancien;
            }
        }
        table[indice].add(new Mapping<K, V>(cle, valeur));
        size++;
        return null;
    }

	/**
	 * Return the value to which the specified key is mapped, or <tt>null</tt>
	 * if this map contains no mapping for the key.
	 * 
	 * @param key
	 *            the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or <tt>null</tt>
	 *         if this map contains no mapping for the key
	 * @throws NullPointerException
	 *             if the key is <tt>null</tt>
	 */
	public V get(Object key) {
        if (key == null) throw new NullPointerException();
        int index = Math.floorMod(key.hashCode(), table.length);
        for (Mapping<K, V> m : table[index]) {
            if (m.getKey().equals(key)) {
                return m.getValue();
            }
        }
        return null;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object cle) {
        return get(cle) != null;
    }

    @Override
    public boolean containsValue(Object valeur) {
        for (Mapping<K, V> truc : this) {
            if (truc.getValue().equals(valeur)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V remove(Object cle) {
        if (cle == null) throw new NullPointerException();
        int indice = Math.floorMod(cle.hashCode(), table.length);
        Iterator<Mapping<K, V>> it = table[indice].iterator();
        while (it.hasNext()) {
            Mapping<K, V> truc = it.next();
            if (truc.getKey().equals(cle)) {
                V val = truc.getValue();
                it.remove();
                size--;
                return val;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i].clear();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> s = new HashSet<>();
        for (Mapping<K, V> truc : this) {
            s.add(truc.getKey());
        }
        return s;
    }

    @Override
    public Collection<V> values() {
        List<V> l = new ArrayList<>();
        for (Mapping<K, V> truc : this) {
            l.add(truc.getValue());
        }
        return l;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> s = new HashSet<>();
        for (Mapping<K, V> truc : this) {
            s.add(new AbstractMap.SimpleEntry<>(truc.getKey(), truc.getValue()));
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map)) return false;
        Map<?, ?> autre = (Map<?, ?>) o;
        return this.entrySet().equals(autre.entrySet());
    }

    @Override
    public int hashCode() {
        return this.entrySet().hashCode();
    }

    private void agrandir() {
        List<Mapping<K, V>>[] vieuxTableau = table;
        table = (List<Mapping<K, V>>[]) new List[vieuxTableau.length * 2];
        for (int i = 0; i < table.length; i++) {
            table[i] = new ArrayList<Mapping<K, V>>();
        }
        size = 0;
        Iterator<Mapping<K, V>> it = new HashIterator(vieuxTableau);
        while (it.hasNext()) {
            Mapping<K, V> m = it.next();
            put(m.getKey(), m.getValue());
        }
    }

    public Iterator<Mapping<K, V>> iterator() {
        return new HashIterator(table);
    }

    private class HashIterator implements Iterator<Mapping<K, V>> {
        private int indiceSeau;
        private int indiceTruc;
        private List<Mapping<K, V>>[] tab;

        public HashIterator(List<Mapping<K, V>>[] t) {
            tab = t;
            indiceSeau = 0;
            indiceTruc = 0;
            avancer();
        }

        public HashIterator() {
            this(table);
        }

        private void avancer() {
            while (indiceSeau < tab.length && indiceTruc >= tab[indiceSeau].size()) {
                indiceSeau++;
                indiceTruc = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return indiceSeau < tab.length;
        }

        @Override
        public Mapping<K, V> next() {
            Mapping<K, V> m = tab[indiceSeau].get(indiceTruc);
            indiceTruc++;
            avancer();
            return m;
        }
    }

	}

