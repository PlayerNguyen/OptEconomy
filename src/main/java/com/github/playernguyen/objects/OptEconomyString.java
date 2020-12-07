package com.github.playernguyen.objects;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * The Preprocess OptEconomy String object. This class save the
 * String util player class;
 */
public class OptEconomyString {

    private final OptEconomy instance;
    private String holder;
    private final Map<String, String> replaceMap = new HashMap<>();

    /**
     * Constructor with define string
     *
     * @param holder the string to be initiated
     */
    public OptEconomyString(OptEconomy instance,
                            String holder) {
        this.instance = instance;
        this.holder = holder;
    }

    /**
     * Constructor with empty string
     */
    public OptEconomyString() {
        this.instance = OptEconomy.inst();
        this.holder = "";
    }

    /**
     * Return the replaced string
     *
     * @return the replaced string
     */
    private String parse() {
        for (Map.Entry<String, String> stringEntry : replaceMap.entrySet()) {
            holder = holder.replace(stringEntry.getKey(), stringEntry.getValue());
        }
        return holder;
    }

    /**
     * Replace single key - value pair
     *
     * @param key   the target key to be replaced
     * @param value the target value to be replaced
     * @return the current initiated object
     */
    public OptEconomyString replace(String key, String value) {
        replaceMap.put(key, value);
        return this;
    }

    /**
     * Replace the string
     *
     * @param pairs the pair to replace
     * @return the current initiated object
     */
    @SafeVarargs
    public final OptEconomyString replace(OptEconomyPair<String, String>... pairs) {
        for (OptEconomyPair<String, String> pair : pairs) {
            replaceMap.put(pair.getFirst(), pair.getSecond());
        }
        return this;
    }

    /**
     * Replace multi pairs
     *
     * @param map the map to be replaced
     * @return the current initiated object
     */
    public OptEconomyString replace(Map<String, String> map) {
        replaceMap.putAll(map);
        return this;
    }

    /**
     * @return the string holder object
     */
    @Override
    public String toString() {
        return this.parse();
    }

    /**
     *
     * @return the placeholder string
     */
    public String toStringWithPlaceholder(Player player) {
        return (instance.getAPIManager().isEnabled(OptEconomyConstants.PLUGIN_NAME_PLACEHOLDER_API))
                ? PlaceholderAPI.setPlaceholders(player, this.holder)
                : holder;
    }

    /**
     *
     * @return instance of OptEconomy
     */
    public OptEconomy getInstance() {
        return instance;
    }
}
