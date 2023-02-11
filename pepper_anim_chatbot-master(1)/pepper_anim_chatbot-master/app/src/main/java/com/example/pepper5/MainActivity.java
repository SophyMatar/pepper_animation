package com.example.pepper5;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.GoToBuilder;
import com.aldebaran.qi.sdk.builder.LocalizeAndMapBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.builder.TransformBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Actuation;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.actuation.Frame;
import com.aldebaran.qi.sdk.object.actuation.FreeFrame;
import com.aldebaran.qi.sdk.object.actuation.GoTo;
import com.aldebaran.qi.sdk.object.actuation.LocalizeAndMap;
import com.aldebaran.qi.sdk.object.actuation.Mapping;
import com.aldebaran.qi.sdk.object.conversation.BodyLanguageOption;
import com.aldebaran.qi.sdk.object.conversation.ConversationStatus;
import com.aldebaran.qi.sdk.object.conversation.QiChatExecutor;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.geometry.Transform;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {

    private Chat chat;
    private GoTo goTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);
    }

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.

        Topic topic = TopicBuilder.with(qiContext).withResource(R.raw.hello_world).build();

        QiChatbot qiChatbot = QiChatbotBuilder.with(qiContext).withTopic(topic).build();

        Map<String, QiChatExecutor> executors = new HashMap<>();
        executors.put("animationExecuter", new AnimationExecuter(qiContext));

        qiChatbot.setExecutors(executors);
        qiChatbot.setSpeakingBodyLanguage(BodyLanguageOption.DISABLED);
        Chat chat = ChatBuilder.with(qiContext).withChatbot(qiChatbot).build();
        chat.async().run();

//        animate = AnimateBuilder.with(qiContext) // Create the builder with the context.
//                .withAnimation(sugar) // Set the animation.
//                .build(); // Build the animate action.
//
//        Future<Void> animateFuture = animate.async().run();


//        animate.addOnLabelReachedListener((label, time) -> {
//            Animation animation = AnimationBuilder.with(qiContext).withResources(R.raw.trajectory_paused).build();
//            Animate animate = AnimateBuilder.with(qiContext).withAnimation(animation).build();
//
//            animate.run();
//
//        });
    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
        if(chat != null) {
            chat.removeAllOnStartedListeners();
        }
        if (goTo != null) {
            goTo.removeAllOnStartedListeners();
        }
//        if (animate != null) {
//            animate.removeAllOnLabelReachedListeners();
//        }
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}
