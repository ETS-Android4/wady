package com.mpv.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.game.players.Player;
import com.mpv.game.world.GameObject;

public class ContactHandler implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		Body 
			a = contact.getFixtureA().getBody(),
			b = contact.getFixtureB().getBody(),
			p = Player.getInstance().body,
			body = (p == a) ? b : a;
		if (body == GameObject.exit) {
			GameObject.getInstance().gameFinish();
		}
		if (p.getLinearVelocity().len() >= Const.BLOCK_SIZE * 10f) {
			if ((int)p.getLinearVelocity().len() % 2 == 0) {
				Assets.playSnd(Assets.oySound);
			} else 
				Assets.playSnd(Assets.uffSound);
				
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}

}