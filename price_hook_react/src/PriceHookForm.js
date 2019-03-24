import React from 'react';
import axios from 'axios';
import './price_hook.css';
import { Form, } from 'reactstrap';
import { ShowOutput, ShowValidEmailArea, ValidButton, ShowValidUrlArea } from './helper-components.js';


class PriceHookForm extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      header:"",
      email: "",
      searchUrl: "",
      productName: "",
      productPrice: "",
      outputFetched: false,
      validEmail: false,
      validUrl: false,
    }

    this.handleClick = this.handleClick.bind(this);
    this.handleUserInput = this.handleUserInput.bind(this);
  }

  validateUrl(url) {
    var re = /^(http[s]?:\/\/){0,1}(www\.){0,1}[a-zA-Z0-9.-]+\.[a-zA-Z]{2,5}[.]{0,1}/;
    if (!re.test(url)) {
      return false;
    } else {
      return true;
    }
  }

  validateEmail(email) {
    if (/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
      return true;
    }
    return false;
  }

  handleUserInput(e) {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value });

    if (name === 'email') {
      if (!this.validateEmail(value)) {
        this.setState({ validEmail: false });
        console.log("Invalid email");
      } else {
        this.setState({ validEmail: true });
        console.log("Valid email");
      }
    } else if (name === 'searchUrl') {
      if (!this.validateUrl(value)) {
        this.setState({ validUrl: false });
        console.log("Invalid url");
      } else {
        this.setState({ validUrl: true });
        console.log("Valid url");
      }
    }
  }

  async handleClick() {
    let urlStart = 'http://localhost:3000/getprice';

    let postData = {
      'product_url': this.state.searchUrl,
      'user_email': this.state.email,
    }

    const response = await axios.post(urlStart, postData);
    console.log(response.data)

    const opHeader = response.data.header;
    
    if(opHeader !== "0"){
      this.setState({
        header: opHeader,
        outputFetched: true
      })
    }else{
      this.setState({
        productName: response.data.product,
        productPrice: response.data.price,
        header: response.data.header,
        outputFetched: true
      });
    }
  }

  render() {
    return (
      <div className="body-background">

        <div className="form-container">
          <h2 className="center">Price Hook</h2>
          <Form>

            <ShowValidUrlArea searchUrl={this.state.searchUrl}
              validUrl={this.state.validUrl}
              handleUserInput={this.handleUserInput}
              validateUrl={this.validateUrl} />

            <ShowValidEmailArea email={this.state.email}
              validEmail={this.state.validEmail}
              handleUserInput={this.handleUserInput}
              validateUrl={this.validateUrl} />

            <div className="center">
            <ValidButton handleClick={this.handleClick}
              validEmail={this.state.validEmail}
              validUrl={this.state.validUrl} />
            </div>
          </Form>

          <ShowOutput outputFetched={this.state.outputFetched} outputHeader={this.state.header}
            productName={this.state.productName} price={this.state.productPrice} />

        </div>

      </div>
    );
  }
}


export default PriceHookForm;