import React, { useState } from "react";
import Axios from "axios";

export default () => {
  const [title, setTitle] = useState("");
  const onSubmit = async (event) => {
    event.preventDefault();

    await Axios.post("http://localhost:4000/posts", {
      title,
    });

    setTitle(""); // reset title
  };
  return (
    <div>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <label>Title</label>
          <input
            className="form-control"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
        <button className="btn btn-primary">Submit</button>
      </form>
    </div>
  );
};
