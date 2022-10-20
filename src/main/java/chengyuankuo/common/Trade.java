package chengyuankuo.common;

import java.io.Serializable;

import lombok.ToString;

/**
 * 交易
 * @author chengyuankuo
 */
@ToString
public class Trade implements Serializable {
    public String symbol;
    public double price;
    public int size;
}
