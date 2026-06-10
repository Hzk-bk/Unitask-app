import {setGlobalOptions} from "firebase-functions";
import {
  onDocumentCreated,
  onDocumentUpdated,
} from "firebase-functions/v2/firestore";
import * as admin from "firebase-admin";

admin.initializeApp();

setGlobalOptions({maxInstances: 10});

export const onTaskCreated = onDocumentCreated(
  "tasks/{taskId}",
  async (event) => {
    const task = event.data?.data();

    await admin.firestore()
      .collection("notifications")
      .add({
        message: `New task created: ${task?.title}`,
        read: false,
        timestamp: admin.firestore.FieldValue.serverTimestamp(),
      });
  }
);

export const onTaskUpdated = onDocumentUpdated(
  "tasks/{taskId}",
  async (event) => {
    const after = event.data?.after.data();

    await admin.firestore()
      .collection("notifications")
      .add({
        message: `Task updated: ${after?.title}`,
        read: false,
        timestamp: admin.firestore.FieldValue.serverTimestamp(),
      });
  }
);
