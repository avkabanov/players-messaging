package com.kabanov.messaging.transport;

import org.junit.Assert;
import org.junit.Test;

import com.kabanov.messaging.player.messages.Message;
import com.kabanov.messaging.transport.data.Parcel;

/**
 * @author Kabanov Alexey
 */
public class InMemoryParcelTransportTest {
    
    private InMemoryParcelTransport inMemoryParcelTransport = new InMemoryParcelTransport();
    
    @Test
    public void shouldBeAbleToTransferTheMessageBetweenTwoComponents() {
        final String firstComponent = "Player1";
        final String secondComponent = "Player2";

        Message toSecondComponentMessage = new Message("hello");
        Message toFirstComponentMessage = new Message("world");
        inMemoryParcelTransport.send(new Parcel(secondComponent, toSecondComponentMessage));
        inMemoryParcelTransport.send(new Parcel(firstComponent, toFirstComponentMessage));

        Assert.assertEquals(toSecondComponentMessage, inMemoryParcelTransport.receive(secondComponent).getBody());
        Assert.assertEquals(toFirstComponentMessage, inMemoryParcelTransport.receive(firstComponent).getBody());
    }

}