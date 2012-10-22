package info.chenliang.fatrock;

import info.chenliang.debug.Assert;
import info.chenliang.ds.Matrix4x4;
import info.chenliang.ds.Precision;
import info.chenliang.ds.Vector3d;

public class Camera {
	public Vector3d position;
	public Vector3d lookAt;
	public Vector3d up;
	public float viewAngle;
	public float nearZ, farZ;
	public float aspectRatio;
	
	public Matrix4x4 worldToCameraTransform;
	public Matrix4x4 cameraToProjectionTransform;

	public Matrix4x4 projectionToScreenTransform;
	public int screenWidth, screenHeight;
	public int screenXOffset, screenYOffset;
	
	public ViewFrustum viewFrustum;
	
	public Camera(Vector3d position, Vector3d lookAt, Vector3d up,
			float viewAngle, float nearZ, float farZ, 
			int screenWidth, int screenHeight, int screenXOffset, int screenYOffset) {
		super();
		this.position = position;
		this.lookAt = lookAt;
		this.up = up;
		this.viewAngle = viewAngle;
		this.nearZ = nearZ;
		this.farZ = farZ;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.screenXOffset = screenXOffset;
		this.screenYOffset = screenYOffset;
		this.aspectRatio = (float)(screenWidth*1.0/screenHeight);
		
		worldToCameraTransform = new Matrix4x4();
		cameraToProjectionTransform = new Matrix4x4();
		projectionToScreenTransform = new Matrix4x4();
		viewFrustum = new ViewFrustum();
		
		
		updateWorldToCameraTransform();
		updateCameraToProjectionTransform();
		updateProjectionToScreenTransform();
		
		viewFrustum.update(cameraToProjectionTransform.multiply(worldToCameraTransform));
	}
	
	public void setScreenOffsets(int screenXOffset, int screenYOffset)
	{
		this.screenXOffset = screenXOffset;
		this.screenYOffset = screenYOffset;
		updateProjectionToScreenTransform();
	}
	
	private void updateProjectionToScreenTransform()
	{
		//x' = screenWidth/2 * x + screenWidth/2 + screenXOffset;
		//y' = -screenHeight/2 * y + screenHeight/2 + screenYOffset;
		
		float screenWidthHalf = screenWidth*1.0f/2;
		float screenHeightHalf = screenHeight*1.0f/2;
		
		projectionToScreenTransform.set(screenWidthHalf, 0, 0, screenWidthHalf + screenXOffset, 
										0, -screenHeightHalf, 0, screenHeightHalf + screenYOffset, 
										0, 0, 1, 0, 
										0, 0, 0, 1);
	}
	
	private void updateWorldToCameraTransform()
	{
		Vector3d zAxis = new Vector3d(position, lookAt);
		zAxis.normalize();
		
		Vector3d yAxis = up;
		yAxis.normalize();
		if(Precision.getInstance().equals(yAxis.dot(zAxis), 1))
		{
			yAxis.setX(yAxis.getX() + 0.5f);
			yAxis.normalize();
		}
		
		Vector3d xAxis = up.cross(zAxis);
		xAxis.normalize();
		
		float dx = position.dot(xAxis);
		float dy = position.dot(yAxis);
		float dz = position.dot(zAxis);
		
		worldToCameraTransform.set(xAxis.getX(), xAxis.getY(), xAxis.getZ(), -dx,
								   yAxis.getX(), yAxis.getY(), yAxis.getZ(), -dy,
								   zAxis.getX(), zAxis.getY(), zAxis.getZ(), -dz,
								   0		   , 0			 , 0		   ,  1);
	}
	
	private void updateCameraToProjectionTransform()
	{
		float viewAngleInRadian = (float)(Math.toRadians(viewAngle/2));
		Assert.judge(!Precision.getInstance().equals(viewAngleInRadian, 0.0f), "View angle should be greater than zero!");
		
		float d = (float)(1 / Math.tan(viewAngleInRadian));
		
		Assert.judge(!Precision.getInstance().equals(nearZ, farZ), "near and far z should not be the same!");
		
		/*
		 * We need to set A && B so that for bigger z, the projected z is also bigger
		 * So we can employ different fules
		 * 1 f(far) = 1 & f(near) = -1   A =  (f + n) / (f - n)    B = - 2 * f * n / (f - n)
		 * 2 f(far) = 1 & f(near) = 0    A =  f / (f - n)			B = - f * n / (f - n)
		 * f(far) = -1 & f(near) = 1	  A = (- f - n) / (f - n)   B = (2 * n * f) / (f - n)
		 */
		
		//| d/AR  0 0 0|
		//| 0     d 0 0|
		//| 0     0 A B|
		//| 0     0 1 0|
		
		/*
		float A = (farZ + nearZ) / (farZ - nearZ);
		float B = -2 * farZ * nearZ / (farZ - nearZ);
		*/
		
		float A = farZ / (farZ - nearZ);
		float B = - farZ * nearZ / (farZ - nearZ);
		cameraToProjectionTransform.set(d/aspectRatio, 0, 0, 0,
										0, d, 0, 0, 
										0, 0, A, B, 
										0, 0, 1, 0);
	}
	
	public void setWorldToCameraTransform(Matrix4x4 worldToCameraTransform) {
		this.worldToCameraTransform = worldToCameraTransform;
	}

	public Matrix4x4 getCameraToProjectionTransform() {
		return cameraToProjectionTransform;
	}

	public void setCameraToProjectionTransform(Matrix4x4 cameraToProjectionTransform) {
		this.cameraToProjectionTransform = cameraToProjectionTransform;
	}
	

	public Matrix4x4 getProjectionToScreenTransform() {
		return projectionToScreenTransform;
	}

	public void setProjectionToScreenTransform(Matrix4x4 projectionToScreenTransform) {
		this.projectionToScreenTransform = projectionToScreenTransform;
	}
	
	public Matrix4x4 getWorldToCameraTransform() {
		return worldToCameraTransform;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
}
