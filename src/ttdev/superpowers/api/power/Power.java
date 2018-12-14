package ttdev.superpowers.api.power;

import org.bukkit.event.Event;

public interface Power<T extends Event> {

    void use(T event);

}
