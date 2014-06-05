package com.mpv.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mpv.data.Assets;
import com.mpv.data.Const;
import com.mpv.game.players.Player;

public class ContactHandler implements ContactListener{


	public void init() {

	}
	
	@Override
	public void beginContact(Contact contact) {
		if (Player.getInstance().body.getLinearVelocity().len() >= Const.BLOCK_SIZE) {
			Assets.playSnd(Assets.edgeSound);
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