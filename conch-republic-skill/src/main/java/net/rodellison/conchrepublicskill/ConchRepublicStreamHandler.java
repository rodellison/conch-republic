package net.rodellison.conchrepublicskill;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import net.rodellison.conchrepublicskill.handlers.*;

public class ConchRepublicStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {

        return Skills.standard()
                .addRequestHandlers(
                        new FullyBakedAskIntentHandler(),
                        new NextIntentHandler(),
                        new CancelandStopIntentHandler(),
                        new StartOverIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new FallBackIntentHandler())
                .withSkillId(System.getenv("SKILL_ID"))
                .build();
    }

    public ConchRepublicStreamHandler() {
        super(getSkill());
    }

}
