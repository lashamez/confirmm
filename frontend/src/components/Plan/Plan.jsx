import React, {useState} from 'react';

import {plans} from "../Const/PlansConstants";
import {Carousel, CarouselItem, CarouselCaption} from "react-bootstrap"
export default function Plans() {
    const [index, setIndex] = useState(0);

    const handleSelect = (selectedIndex, e) => {
        setIndex(selectedIndex);
    };

    return (
        <Carousel activeIndex={index} onSelect={handleSelect}>
            {plans.map(plan => {
                return (
                    <CarouselItem>
                        <img
                            className="d-block w-100"
                            src={plan.image}
                            alt={plan.label}
                        />

                        <CarouselCaption>
                            <h3>{plan.label}</h3>
                            <p>
                                {plan.registrationFee}ლ {plan.users} მომხმარებელი
                            </p>
                        </CarouselCaption>
                    </CarouselItem>
                )
            })}
        </Carousel>
    );
}
