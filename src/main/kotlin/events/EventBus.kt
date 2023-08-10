package events

import com.yerti.core.YertiPlugin
import extensions.colorPrefix
import org.bukkit.entity.Player
import org.bukkit.event.*
import org.bukkit.plugin.RegisteredListener
import java.util.*
import kotlin.reflect.KClass

class EventBus : Listener {

    companion object {
        val instance = EventBus()
    }

    //Subscribe to every event for checking
    init {
        val registeredListener = RegisteredListener(this, { _, event -> onEvent(event) }, EventPriority.NORMAL, YertiPlugin.getHookedPlugin(), false)
        HandlerList.getHandlerLists().forEach { it.register(registeredListener) }
    }

    val listeners = IdentityHashMap<KClass<*>, MutableList<(Any) -> (Boolean)>>()

    inline fun <reified Type : Event> next(
            noinline condition: (Type) -> Boolean,
            noinline action: (Type) -> Unit) = listeners.getOrPut(Type::class, ::ArrayList).add {

        val result = condition(it as Type)
        if (result) action(it); result

    }

    inline fun <reified Type : Event> listen(
            noinline condition: (Type) -> Boolean,
            noinline action: (Type) -> Unit) = listeners.getOrPut(Type::class, ::ArrayList).add {

        if (condition(it as Type)) action(it); false
    }

    @EventHandler
    fun onEvent(event: Event) = when (event) {
        //Currently left blank, any custom event wrappers will be put here
        else -> event
    }.let {
        listeners[it::class]?.removeIf { predicate -> predicate(it) }; Unit
    }


}