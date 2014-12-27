package com.mpv.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.game.actors.Player;
import com.mpv.game.world.GameObject;
import com.mpv.game.world.Position;

public class ContactHandler implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Player pl = Player.getInstance();
		if (pl.body.getLinearVelocity().len() >= Const.BLOCK_SIZE * 13f) {
			Assets.hitEffect.setPosition(pl.getX() + pl.getOriginX(), pl.getY() + pl.getOriginY());
			Assets.hitEffect.start();
			Assets.playSnd(Assets.hit1Snd);
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Player pl = Player.getInstance();
		Body a = contact.getFixtureA().getBody(), b = contact.getFixtureB().getBody(), p = pl.body;

		if (p != a && p != b)
			return;
		Body body = (p == a) ? b : a;
		Object data = body.getUserData();

		if (null != data && data instanceof Position) {
			contact.setEnabled(false);
			GameObject.collectCoin(body);
			return;
		}

		if (null != GameObject.key) {
			if (body == GameObject.key) {
				GameObject.captureKey();
				contact.setEnabled(false);
				return;
			}
		} else {
			if (body == GameObject.exit) {
				GameObject.getInstance().gameFinish();
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
		// TODO Auto-generated method stub
	}

}