import React from 'react';
import { Button, FormGroup, Input, Label, FormFeedback } from 'reactstrap';


function ServiceDoesNotExist(){

    return (<div className="service-does-not-exist">
    <span>Price Hook does not support this website currently! <br/>
    Soon we will add support for it. <br/>
    Thank you for using Price Hook!</span>
    </div>
    );

}

function CouldNotFindXpath(){
    return(
        <div className="could-not-find-xpath">
        <span>
            Price Hook could not retrieve a product name or price on this link.
            <br/>
            Please make sure that the link you entered is correct.
        </span>
        </div>
    );
}

function MalformedURLError(){
    return(
        <div class="error-message">
        <span>Entered url is malformed. Please enter the url in format "http(s)://".</span>
        </div>
    );
}

function UnknownError(){
    return(
        <div className="unknown-error">
        <span>Something went wrong with Price Hook. <br/>
        Please try again later.
        </span>
        </div>
    );
}


function OutputText(props) {
    return (<div className="showoutput">
        <p>Product Name: {props.productName}</p>
        <p>Price: {props.price}</p>
    </div>)
}

function ShowOutput(props) {
    const opfetched = props.outputFetched;
    const outputHeader = props.outputHeader;
    if(outputHeader === "-1" && opfetched){
        return <ServiceDoesNotExist/>
    }else if (outputHeader==="0" && opfetched) {
        return <OutputText productName={props.productName} price={props.price} />;
    }else if(outputHeader==="1" && opfetched){
        return <CouldNotFindXpath/>
    }else if(outputHeader==="2" && opfetched){
        return <MalformedURLError/>
    }else if((outputHeader==="3" || outputHeader==="4")  && opfetched){
        return <UnknownError/>
    }
    return null;
}

function ShowValidUrlArea(props) {
    console.log("Rendering feedback new one " + props.searchUrl + " bool " + props.validUrl)
    if (props.searchUrl === "" && !props.validUrl) {
        return (
            <FormGroup>
                <Label for="searchUrl">Add URL</Label>

                <Input type="text" name="searchUrl" id="searchUrl" placeholder="url..."
                    onChange={(event) => props.handleUserInput(event)} />
            </FormGroup>
        );
    } else if (props.validUrl) {
        return (
            <FormGroup>
                <Label for="searchUrl">Add URL</Label>
                <Input type="text" name="searchUrl" id="searchUrl" placeholder="url..."
                    onChange={(event) => props.handleUserInput(event)} valid />
            </FormGroup>
        );
    } else if (!props.validUrl) {
        return (
            <FormGroup>
                <Label for="searchUrl">Add URL</Label>

                <Input type="text" name="searchUrl" id="searchUrl" placeholder="url..."
                    onChange={(event) => props.handleUserInput(event)} invalid />
                <FormFeedback>Entered URL is not in proper format</FormFeedback>
            </FormGroup>
        );
    }
    return null;
}

function ShowValidEmailArea(props) {
    console.log("Rendering feedback new one " + props.email + " bool " + props.validEmail)
    if (props.email === "" && !props.validEmail) {
        return (
            <FormGroup>
                <Label for="email">Add URL</Label>
                <Input type="email" name="email" id="email" placeholder="abcd@example.com"
                    onChange={(event) => props.handleUserInput(event)} />
            </FormGroup>
        );
    } else if (props.validEmail) {
        return (
            <FormGroup>
                <Label for="email">Add URL</Label>
                <Input type="email" name="email" id="email" placeholder="abcd@example.com"
                    onChange={(event) => props.handleUserInput(event)} valid />
            </FormGroup>);
    } else if (!props.validUrl) {
        return (
            <FormGroup>
                <Label for="email">Add URL</Label>
                <Input type="email" name="email" id="email" placeholder="abcd@example.com"
                    onChange={(event) => props.handleUserInput(event)} invalid />
                <FormFeedback>Entered email is not in proper format</FormFeedback>
            </FormGroup>
        );
    }
    return null;
}

function ValidButton(props) {

    if (props.validEmail && props.validUrl) {
        return (
            <Button outline onClick={props.handleClick} color='primary' >Hook it up!</Button>
        );
    }
    return (<Button outline onClick={props.handleClick} color='primary' disabled>Hook it up!</Button>);
}

export { ShowOutput, ShowValidEmailArea, ShowValidUrlArea, ValidButton }