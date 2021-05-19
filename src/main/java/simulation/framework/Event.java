package simulation.framework;

public interface Event<S> {
  void invoke(S simulation);
}
