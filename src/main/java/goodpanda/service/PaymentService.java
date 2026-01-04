package goodpanda.service;

import goodpanda.domain.Payment;
import goodpanda.domain.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 26/11/24
 */
@Service
public class PaymentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrUpdatePayment(Payment payment) {
        if (isNull(payment.getId())) {
            entityManager.persist(payment);

            return;
        }

        entityManager.merge(payment);
    }

    public Payment getPaymentById(int id) {
        return entityManager.find(Payment.class, id);
    }

    public List<Payment> getAllPayments() {
        String jpql = "SELECT p FROM Payment p";

        return entityManager.createQuery(jpql, Payment.class).getResultList();
    }

    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        String jpql = "SELECT p FROM Payment p WHERE p.paymentStatus = :status";

        return entityManager.createQuery(jpql, Payment.class)
                .setParameter("status", status)
                .getResultList();
    }

    public Payment getPaymentByOrderId(int orderId) {
        String jpql = "SELECT p FROM Payment p WHERE p.order.id = :orderId";

        return entityManager.createQuery(jpql, Payment.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }

    @Transactional
    public void deletePaymentById(int id) {
        Payment payment = entityManager.find(Payment.class, id);

        if (nonNull(payment)) {
            entityManager.remove(payment);
        }
    }

    @Transactional
    public void updatePaymentStatus(int paymentId, PaymentStatus status) {
        Payment payment = entityManager.find(Payment.class, paymentId);

        if (nonNull(payment)) {
            payment.setPaymentStatus(status);
            entityManager.merge(payment);
        }
    }
}
