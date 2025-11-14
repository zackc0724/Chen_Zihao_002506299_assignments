package business;

import javax.swing.JPanel;

/**
 * Abstract Role with RBAC-style work area creation.
 */
public abstract class Role {

    public abstract JPanel createWorkArea(JPanel mainContainer, UserAccount account, LibrarySystem system);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
