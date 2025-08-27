/**
 *
 * @author YEOH YIK YAO
 */
package adt;

public interface EntryInterface<S, T> {
    public S getKey();

    public T getValue();

    public void setValue(T newValue);

    public boolean isIn();

    public boolean isRemoved();

    public void setToRemoved();

    public void setToIn();
}
