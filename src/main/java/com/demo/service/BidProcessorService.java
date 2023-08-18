package com.demo.service;

import com.demo.entity.Bid;
import com.demo.entity.BidStatus;
import com.demo.model.BidResponse;
import com.demo.entity.Car;
import com.demo.repository.BidRepository;
import com.demo.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidProcessorService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BidRepository bidRepository;

    @Transactional
    public void processBids() {
        List<Bid> pendingBids = bidRepository.findByStatus(BidStatus.PENDING);

        for (Bid bid : pendingBids) {
            Car car = bid.getCar();
            if (bid.getBidAmount() < car.getMinBidAmount()) {
                bid.setStatus(BidStatus.REJECTED);
            } else {
                negotiateBid(bid);
            }
            bidRepository.save(bid);
            updateCarListing(car);
        }
    }

    private void negotiateBid(Bid bid) {
        Car car = bid.getCar();
        double initialOffer = calculateInitialOffer(car.getMinBidAmount());
        bid.setBidAmount(initialOffer);

        // Send initial offer to buyer and wait for response
        BidResponse response = sendOfferAndGetResponse(bid);

        if (isWithinAcceptableRange(response, initialOffer)) {
            bid.setStatus(BidStatus.ACCEPTED);
        } else {
            double counterOffer = calculateCounterOffer(initialOffer);
            response = sendOfferAndGetResponse(new Bid(bid.getCar(), bid.getBuyer(), counterOffer));

            if (response.isAccepted()) {
                bid.setBidAmount(counterOffer);
                bid.setStatus(BidStatus.ACCEPTED);
            } else {
                bid.setStatus(BidStatus.REJECTED);
            }
        }
    }

    private double calculateInitialOffer(double minBidAmount) {
        //TODO Define your logic to calculate the initial offer
        return minBidAmount;
    }

    private double calculateCounterOffer(double previousOffer) {
        //TODO Define your logic to calculate counteroffer
        return previousOffer;
    }

    private boolean isWithinAcceptableRange(BidResponse response, double offer) {
        //TODO Define your logic to check if the buyer's response is within acceptable range
        return false;
    }

    private void updateCarListing(Car car) {

        //TODO Update car listing based on bid outcomes
    }

    private BidResponse sendOfferAndGetResponse(Bid bid) {
        //TODO Simulate sending the offer to the buyer and receiving a response
        // This could involve an external API call or simulated response
        return new BidResponse(/* Response details */);
    }
}

