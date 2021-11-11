package telran.mediation;

import java.util.List;

/**
 * This interface represents Blocking Queue for usage
 * as communication buffer between Producers and Consumers
 */
public interface IBlkQueue<T> {
	void push(T message);
	T pop();
}
