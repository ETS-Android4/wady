package com.mpv.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.data.Sounds;
import com.mpv.data.Sounds.ID;
import com.mpv.game.actors.Player;
import com.mpv.game.world.Coin;
import com.mpv.game.world.GameObj;
import com.mpv.game.world.TimeBonus;

public class Collisions implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Player pl = Player.get();
        if (pl.body.getLinearVelocity().len() >= Const.BLOCK_SIZE * 13f) {
            Assets.hitEffect.setPosition(pl.getX() + pl.getOriginX(), pl.getY() + pl.getOriginY());
            Assets.hitEffect.start();
            Sounds.play(ID.HIT);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // No-op
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Player pl = Player.get();
        Body a = contact.getFixtureA().getBody(), b = contact.getFixtureB().getBody(), p = pl.body;

        if (p != a && p != b)
            return;
        Body body = (p == a) ? b : a;
        Object data = body.getUserData();

        if (null != data) {
            if (data instanceof Coin) {
                contact.setEnabled(false);
                GameObj.get().collectDiamond(body);
                return;
            }
            if (data instanceof TimeBonus) {
                contact.setEnabled(false);
                GameObj.get().collectTime(body);
                return;
            }
        }

        if (null != GameObj.key) {
            if (body == GameObj.key) {
                GameObj.captureKey();
                contact.setEnabled(false);
                return;
            }
        } else if (null != GameObj.lock) {
            if (body == GameObj.lock) {
                GameObj.captureLock();
                contact.setEnabled(false);
                return;
            }
        } else {
            if (body == GameObj.start) {
                GameObj.get().gameFinish();
                return;
            }
        }
        // Removing slide effect
        if (Math.abs(p.getLinearVelocity().y) <= Const.BLOCK_SIZE) {
            p.setLinearVelocity(0, p.getLinearVelocity().y);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // No-op
    }
}