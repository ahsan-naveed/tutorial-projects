import React, { useState } from "react";
import Axios from "axios";

export default ({ postId }) => {
  const [content, setContent] = useState("");
  const onSubmit = async (event) => {
    event.preventDefault();

    await Axios.post(`http://localhost:4001/posts/${postId}/comments`, {
      content,
    });

    setContent("");
  };
  return (
    <div>
      <form onSubmit={onSubmit}>
        <div className="form-group">
          <label>New Comment</label>
          <input
            className="form-control"
            value={content}
            onChange={(e) => setContent(e.target.value)}
          ></input>
        </div>
        <button className="btn btn-primary">Submit</button>
      </form>
    </div>
  );
};
