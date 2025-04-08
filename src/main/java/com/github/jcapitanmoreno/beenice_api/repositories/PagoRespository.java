package com.github.jcapitanmoreno.beenice_api.repositories;

import com.github.jcapitanmoreno.beenice_api.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRespository extends JpaRepository<Pago, Integer> {
    /**
     * Encuentra un pago por su ID.
     * @param id el ID del pago.
     * @return el pago con el ID especificado.
     */
    Pago findById(int id);

    /**
     * Encuentra un pago por su monto.
     * @param monto el monto del pago.
     * @return el pago con el monto especificado.
     */
    Pago findByMonto(double monto);
}
