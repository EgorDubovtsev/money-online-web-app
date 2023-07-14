import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
    width: 100%;
    text-align: center;
`

export const Balance = ({balance, currency}) =>{

    return(
        <Wrapper>
            <span>{balance} {currency}</span>
        </Wrapper>
    )
}