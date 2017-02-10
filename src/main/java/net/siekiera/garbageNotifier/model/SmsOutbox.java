package net.siekiera.garbageNotifier.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Eric on 04.02.2017.
 * Encja SMSa wychodzącego.
 */
@Entity
@Table(name = "outbox")
public class SmsOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "destinationnumber")
    private String destinationNumber;

    @Column(name = "textdecoded")
    private String textDecoded;

    @Column(name = "CreatorID")
    private String creatorID = "Program";

    @Column(name = "sendingdatetime")
    private Date sendingDateTime;

    public SmsOutbox(String destinationNumber, String textDecoded) {
        this.destinationNumber = destinationNumber;
        this.textDecoded = textDecoded;
    }

    public SmsOutbox() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getTextDecoded() {
        return textDecoded;
    }

    public void setTextDecoded(String textDecoded) {
        this.textDecoded = textDecoded;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public Date getSendingDateTime() {
        return sendingDateTime;
    }

    public void setSendingDateTime(Date sendingDateTime) {
        this.sendingDateTime = sendingDateTime;
    }
}
