package goodpanda.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author sanjidaera
 * @since 25/11/24
 */
@Entity
@Table(name = "order_table")
public class Order extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderIdSeq")
    @SequenceGenerator(name = "orderIdSeq", sequenceName = "order_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @NotNull
    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "order_placement_time", nullable = false)
    private LocalDateTime orderPlacementTime;

    @Column(name = "order_acceptance_time", nullable = false)
    private LocalDateTime orderAcceptanceTime;

    @Column(name = "rider_assignment_time", nullable = false)
    private LocalDateTime riderAssignmentTime;

    @Column(name = "delivery_time", nullable = false)
    private LocalDateTime deliveryTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rider_id", referencedColumnName = "id")
    private User rider;

    @Lob
    @Column(name = "delivery_proof")
    private byte[] deliveryProof;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE},
            orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToOne
    @JoinColumn(name = "delivery_location_id", referencedColumnName = "id")
    @NotNull
    private Location location;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Transient
    private String deliveryProofBase64;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getOrderPlacementTime() {
        return orderPlacementTime;
    }

    public void setOrderPlacementTime(LocalDateTime orderPlacementTime) {
        this.orderPlacementTime = orderPlacementTime;
    }

    public LocalDateTime getOrderAcceptanceTime() {
        return orderAcceptanceTime;
    }

    public void setOrderAcceptanceTime(LocalDateTime orderAcceptanceTime) {
        this.orderAcceptanceTime = orderAcceptanceTime;
    }

    public LocalDateTime getRiderAssignmentTime() {
        return riderAssignmentTime;
    }

    public void setRiderAssignmentTime(LocalDateTime riderAssignmentTime) {
        this.riderAssignmentTime = riderAssignmentTime;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public User getRider() {
        return rider;
    }

    public void setRider(User rider) {
        this.rider = rider;
    }

    public byte[] getDeliveryProof() {
        return deliveryProof;
    }

    public void setDeliveryProof(byte[] deliveryProof) {
        this.deliveryProof = deliveryProof;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryProofBase64() {
        return deliveryProofBase64;
    }

    public void setDeliveryProofBase64(String deliveryProofBase64) {
        this.deliveryProofBase64 = deliveryProofBase64;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Order)) {
            return false;
        }

        Order order = (Order) o;

        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}