package ru.polskiy.annotations;

import ru.polskiy.model.type.ActionType;

public @interface Auditable {

    String login() default "";
    String userId() default "";
    ActionType actionType();
}
