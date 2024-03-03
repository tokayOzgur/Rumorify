import { fetchUserById } from "@/api/userApi";
import { Alert } from "@/shared/components/Alert";
import { Spinner } from "@/shared/components/Spinner";
import React, { Component, useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export class UserClass extends Component {
  state = {
    user: null,
    apiProgress: false,
    errorMessage: "",
  };

  async componentDidMount() {
    this.setState({ apiProgress: true });
    try {
      const response = await fetchUserById(parseInt(this.props.id));
      this.setState({ user: response.data });
    } catch (error) {
      console.log(error);
      this.setState({
        errorMessage: "Something went wrong! " + error.response.data.message,
      });
    } finally {
      this.setState({ apiProgress: false });
    }
  }

  render() {
    return (
      <div className="container">
        {this.state.user && this.state.user.username}
        {this.state.errorMessage && (
          <Alert styleType="danger">{this.state.errorMessage}</Alert>
        )}
        {this.state.apiProgress && <Spinner size="lg m-auto" />}
      </div>
    );
  }
}

export const User = () => {
  const { id } = useParams();
  return <UserClass id={id} />;
};
