package org.piraven;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Library {
    private List<Item> items;
    private Map<String, User> users;

    /**
     * Recursively searches for items' title contains the given query.
     * @param query the keyword
     * @param items the list of items
     * @param index the index of the item
     * @return a list of items matching the keyword
     */
    public List<Item> searchRecursive(String query, List<Item> items, int index) {
        List<Item> result = new ArrayList<>();

        if (items == null || index >= items.size()) {
            return result;
        }

        Item currentItem = items.get(index);

        if (currentItem.getTitle().toLowerCase().contains(query.toLowerCase())) {
            result.add(currentItem);
        }

        result.addAll(searchRecursive(query, items, index + 1));
        return result;
    }
}
