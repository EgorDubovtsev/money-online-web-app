import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
    width: 100%;
    text-align: center;
`

export const Balance = () =>{
    const [balance, setBalance] = React.useState(0);
    const [currency, setCurrency] = React.useState('RUB');
    return(
        <Wrapper>
            <span>{balance} {currency}</span>
        </Wrapper>
    )
}