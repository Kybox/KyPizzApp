package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.exception.NotFoundException;
import fr.kybox.kypizzapp.model.order.DeliveryMethod;
import fr.kybox.kypizzapp.repository.DeliveryRepository;
import fr.kybox.kypizzapp.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public DeliveryMethod create(DeliveryMethod deliveryMethod) {

        return deliveryRepository.save(deliveryMethod);
    }

    @Override
    public DeliveryMethod update(DeliveryMethod deliveryMethod) {

        Optional<DeliveryMethod> optDeliveryMethod = deliveryRepository.findById(deliveryMethod.getId());

        if(optDeliveryMethod.isPresent()) return deliveryRepository.save(deliveryMethod);
        else throw new NotFoundException("This delivery method was ot found.");
    }

    @Override
    public List<DeliveryMethod> findAll() {

        return deliveryRepository.findAll();
    }

    @Override
    public DeliveryMethod findById(String id) {

        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("The delivery method was not found."));
    }

    @Override
    public List<DeliveryMethod> delete(String id) {

        Optional<DeliveryMethod> optDeliveryMethod = deliveryRepository.findById(id);
        if(!optDeliveryMethod.isPresent())
            throw new NotFoundException("The delivery method was not found.");

        deliveryRepository.delete(optDeliveryMethod.get());

        return deliveryRepository.findAll();
    }
}
