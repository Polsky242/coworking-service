package ru.polskiy.model.type;

/**
 * Enum representing various actions that can be performed related to workspaces.
 * Each action corresponds to a specific operation that can be requested or performed.
 */
public enum ActionType {

    /**
     * Action representing registration of a user.
     */
    REGISTRATION,

    /**
     * Action representing authorization of a user.
     */
    AUTHORIZATION,

    /**
     * Action representing submission of a workspace.
     */
    SUBMIT_WORKSPACE,

    /**
     * Action representing cancellation of a workspace.
     */
    CANCEL_WORKSPACE,

    /**
     * Action representing retrieval of workspaces.
     */
    GETTING_WORKSPACES
}
