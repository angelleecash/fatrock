package info.chenliang.fatrock.trianglerenderers;

import android.graphics.Bitmap;
import android.os.Debug;
import android.util.Log;
import info.chenliang.ds.Precision;
import info.chenliang.ds.Vector2d;
import info.chenliang.ds.Vector3d;
import info.chenliang.ds.Vector4d;
import info.chenliang.fatrock.PixelRenderer;
import info.chenliang.fatrock.Texture;
import info.chenliang.fatrock.Vertex3d;
import info.chenliang.fatrock.ZBuffer;

public class TriangleRenderer {
	public PixelRenderer pixelRenderer;
	public ZBuffer zBuffer;
	public boolean projectionCorrect;
	public Texture texture;
	
	public TriangleRenderer(PixelRenderer pixelRenderer, ZBuffer zBuffer, boolean projectionCorrect, Texture texture)
	{
		this.pixelRenderer = pixelRenderer;
		this.zBuffer = zBuffer;
		this.projectionCorrect = projectionCorrect;
		this.texture = texture;
	}
	
	public void resetZBuffer()
	{
		if(projectionCorrect)
		{
			zBuffer.reset(Float.MIN_VALUE);
		}
		else
		{
			zBuffer.reset(Float.MAX_VALUE);	
		}
		
	}
	
	private Vector3d getRandomColor()
	{
		return new Vector3d((float)Math.random()*256, (float)Math.random()*256, (float)Math.random()*256);
	}
	
	public void fillTriangle(Vertex3d v1, Vertex3d v2, Vertex3d v3)
	{
		Vertex3d temp;
		
		if(v1.transformedPosition.y > v2.transformedPosition.y)
		{
			temp = v1;
			v1 = v2;
			v2 = temp;
		}
		
		if(v1.transformedPosition.y > v3.transformedPosition.y)
		{
			temp = v1;
			v1 = v3;
			v3 = temp;
		}
		
		if(v2.transformedPosition.y > v3.transformedPosition.y)
		{
			temp = v2;
			v2 = v3;
			v3 = temp;
		}
		
		Vector4d p1 = v1.transformedPosition;
		Vector4d p2 = v2.transformedPosition;
		Vector4d p3 = v3.transformedPosition;
		
		float dy31 = p3.y - p1.y;
		
		if(Precision.getInstance().equals(dy31, 0.0f))
		{
			return;
		}
		
		float dy21 = p2.y - p1.y;
		
		float dx31 = p3.x - p1.x;
		float dx21 = p2.x - p1.x;
		
		float cross = dx21*dy31 - dy21*dx31;
		if(cross == 0.0f)
		{
			drawLine3d(p3.degenerate(), p1.degenerate(), 0xff000000);
			return;
		}
		
		Bitmap bitmap = texture.bitmap;
		
		Vector3d color1 = v1.transformedColor;
		Vector3d color2 = v2.transformedColor;
		Vector3d color3 = v3.transformedColor;
		
		Vector2d texturePosition1 = v1.texturePosition;
		Vector2d texturePosition2 = v2.texturePosition;
		Vector2d texturePosition3 = v3.texturePosition;
		
		boolean right = cross > 0;		
		
		float dxLeft = 0.0f, dxRight = 0.0f, dzLeft=0.0f, dzRight=0.0f;
		float dz31 = p3.w - p1.w;
		float dz21 = p2.w - p1.w;
		float _dz31 = 1/p3.w - 1/p1.w;
		
		Vector3d _colorStepLeft = new Vector3d(0, 0, 0);
		Vector3d _colorStepRight = new Vector3d(0, 0, 0);
		
		Vector2d _dTexturePositionLeft = new Vector2d(0.0f, 0.0f);
		Vector2d _dTexturePositionRight = new Vector2d(0.0f, 0.0f);
		
		float oneOverZ1 = 1 / p1.w;
		float oneOverZ2 = 1 / p2.w;
		float oneOverZ3 = 1 / p3.w;
		
		Vector2d texturePositionOverZ1 = new Vector2d(texturePosition1, oneOverZ1);
		Vector2d texturePositionOverZ2 = new Vector2d(texturePosition2, oneOverZ2);
		Vector2d texturePositionOverZ3 = new Vector2d(texturePosition3, oneOverZ3);
		
		if(dy21 > 0.0f)
		{
			int startY = (int)Math.ceil(p1.y);
			int endY = (int)Math.ceil(p2.y) - 1;
			
			int ySpan = endY - startY + 1;
			if(ySpan > 0)
			{
				float _dz21 = 1/p2.w - 1/p1.w;
				float subPixelY = startY - p1.y;
				
				dxLeft = right ? dx31/dy31 : dx21/dy21;
				dxRight = right ? dx21/dy21 : dx31/dy31;
				
				Vector2d texturePositionLeft = null;
				Vector2d texturePositionRight = null;
				
				Vector2d dTexturePositionLeft = null;
				Vector2d dTexturePositionRight = null;
				
				if(projectionCorrect)
				{
					dzLeft = right ? _dz31/dy31 : _dz21/dy21;
					dzRight = right ? _dz21/dy21 : _dz31/dy31;
					
					texturePositionLeft = new Vector2d(texturePositionOverZ1);
					texturePositionRight = new Vector2d(texturePositionOverZ1);
					
					dTexturePositionLeft = right?texturePositionOverZ3.minus(texturePositionOverZ1):texturePositionOverZ2.minus(texturePositionOverZ1);
					dTexturePositionLeft.scale(right?1/dy31:1/dy21);
					
					dTexturePositionRight = right?texturePositionOverZ2.minus(texturePositionOverZ1):texturePositionOverZ3.minus(texturePositionOverZ1);
					dTexturePositionRight.scale(right?1/dy21:1/dy31);
				}
				else
				{
					dzLeft = right ? dz31/dy31 : dz21/dy21;
					dzRight = right ? dz21/dy21 : dz31/dy31;
					
					texturePositionLeft = texturePosition1;
					texturePositionRight = texturePosition1;
					
					dTexturePositionLeft = right?texturePosition3.minus(texturePosition1):texturePosition2.minus(texturePosition1);
					dTexturePositionLeft.scale(right?1/dy31:1/dy21);
					
					dTexturePositionRight = right?texturePosition2.minus(texturePosition1):texturePosition3.minus(texturePosition1);
					dTexturePositionRight.scale(right?1/dy21:1/dy31);
				}
				
				_dTexturePositionLeft.copy(dTexturePositionLeft);
				_dTexturePositionRight.copy(dTexturePositionRight);
				
				Vector3d colorStepLeft = right?color3.minus(color1):color2.minus(color1);
				Vector3d colorStepRight = right?color2.minus(color1):color3.minus(color1);
				
				Vector3d colorLeft = new Vector3d(color1);
				Vector3d colorRight = new Vector3d(color1);
				
				colorStepLeft.scale(right?1/dy31:1/dy21);
				colorStepRight.scale(right?1/dy21:1/dy31);
				
				_colorStepLeft.copy(colorStepLeft);
				_colorStepRight.copy(colorStepRight);

				float zLeft = projectionCorrect ? 1/p1.w : p1.w + dzLeft*subPixelY;
				float zRight = projectionCorrect ? 1/p1.w : p1.w + dzRight*subPixelY;
				
				float xLeft = p1.x + dxLeft*subPixelY;
				float xRight = p1.x + dxRight*subPixelY;
				
				for(int y=startY; y <= endY; y++)
				{
					int startX = (int)Math.ceil(xLeft);
					int endX = (int)Math.ceil(xRight) - 1;
					int xSpan = endX - startX + 1;
					
					if(xSpan > 0)
					{
						float dz = (zRight - zLeft) / xSpan;
						float z = zLeft;
						float subPixelX = startX - xLeft;
						z += subPixelX*dz;
						Vector3d _color = new Vector3d(colorLeft); 
						Vector3d _colorStep = colorRight.minus(colorLeft);
						_colorStep.scale(1.0f/xSpan);
						
						Vector2d texturePositionStep = texturePositionRight.minus(texturePositionLeft);
						texturePositionStep.scale(1.0f/xSpan);
						
						Vector2d texturePosition = new Vector2d(texturePositionLeft);
						for(int x=startX; x <= endX; x++)
						{
							float _z = zBuffer.getZ(x, y);
							if(zBuffer.zBufferComparer.compare(_z, z))						
							{
								Vector2d _texturePosition = new Vector2d(texturePosition);
								
								if(projectionCorrect)
								{
									_texturePosition.scale(1/z);									
								}
								
								int textureX = (int)(_texturePosition.x * (bitmap.getWidth()-1));
								int textureY = (int)(_texturePosition.y * (bitmap.getHeight()-1));
								//Log.d("shit", _texturePosition.x+" " + _texturePosition.y + " " + textureX + " " + textureY);
								textureX = clamp(textureX, 0, bitmap.getWidth() - 1);
								textureY = clamp(textureY, 0, bitmap.getHeight() - 1);
								//if(true)continue;
								int textureColor = bitmap.getPixel(textureX, textureY);
								
								pixelRenderer.setPixel(x, y, textureColor);
								//pixelRenderer.setPixel(x, y, _color.asColor());
								//pixelRenderer.setPixel(x, y, right?0xffff0000:0xff00ff00);
								zBuffer.setZ(x, y, z);					
							}
							
							z += dz;
							_color = _color.add(_colorStep);
							
							texturePosition = texturePosition.add(texturePositionStep);
						}
					}
					
					xLeft += dxLeft;
					xRight += dxRight;
					
					zLeft += dzLeft;
					zRight += dzRight;
					
					colorLeft = colorLeft.add(colorStepLeft);
					colorRight = colorRight.add(colorStepRight);
					
					texturePositionLeft = texturePositionLeft.add(dTexturePositionLeft);
					texturePositionRight = texturePositionRight.add(dTexturePositionRight);
					
				}
			}
				
		}

		float dy32 = p3.y - p2.y;
		if(dy32 > 0.0f)
		{
			int startY = (int)Math.ceil(p2.y);
			int endY = (int)Math.ceil(p3.y) - 1;
			int ySpan = endY - startY + 1;
			if(ySpan > 0)
			{
				float subPixelY = startY - p2.y;;
				
				float dx32 = p3.x - p2.x;
				
				float xLeft = right? p1.x+dy21*dxLeft : p2.x;
				float xRight = right ? p2.x : p1.x+dy21*dxRight;
				
				float zLeft = 0.0f;
				float zRight = 0.0f;
				
				float dz32 = p3.w - p2.w;
				float _dz32 = 1/p3.w - 1/p2.w;
				
				Vector2d texturePositionLeft = null;
				Vector2d texturePositionRight = null;
				
				Vector2d dTexturePositionLeft = null;
				Vector2d dTexturePositionRight = null;
				
				texturePositionLeft = right?texturePositionOverZ1.add(_dTexturePositionLeft.scale2(dy21)):new Vector2d(texturePositionOverZ2);
				texturePositionRight = right?new Vector2d(texturePositionOverZ2):texturePositionOverZ1.add(_dTexturePositionRight.scale2(dy21));
				
				if(projectionCorrect)
				{
					zLeft = right ? 1/p1.w+dy21*dzLeft : 1/p2.w;
					zRight = right ? 1/p2.w: 1/p1.w+dzRight*dy21;
					
					dTexturePositionLeft = right?texturePositionOverZ3.minus(texturePositionOverZ1):texturePositionOverZ3.minus(texturePositionOverZ2);
					dTexturePositionRight = right?texturePositionOverZ3.minus(texturePositionOverZ2):texturePositionOverZ3.minus(texturePositionOverZ1);
				}
				else
				{
					zLeft = right ? p1.w+dy21*dzLeft : p2.w;
					zRight = right ? p2.w: p1.w+dzRight*dy21;
					
					dTexturePositionLeft = right?texturePosition3.minus(texturePosition1):texturePosition3.minus(texturePosition2);
					dTexturePositionRight = right?texturePosition3.minus(texturePosition2):texturePosition3.minus(texturePosition1);
				}
				
				dTexturePositionLeft.scale(right?1/dy31:1/dy32);
				dTexturePositionRight.scale(right?1/dy32:1/dy31);
				
				dxLeft = right ? dx31/dy31 : dx32/dy32;
				dxRight = right ? dx32/dy32 : dx31/dy31;
				
				xLeft += subPixelY*dxLeft;
				xRight += subPixelY*dxRight;

				if(projectionCorrect)
				{
					dzLeft = right ? _dz31/dy31 : _dz32/dy32;
					dzRight = right ? _dz32/dy32 : _dz31/dy31;
				}
				else
				{
					dzLeft = right ? dz31/dy31 : dz32/dy32;
					dzRight = right ? dz32/dy32 : dz31/dy31;	
				}
				
				Vector3d colorLeft = right ? color1.add(_colorStepLeft.scale2(dy21)) : new Vector3d(color2);
				Vector3d colorRight = right? new Vector3d(color2) : color1.add(_colorStepRight.scale2(dy21));
				
				Vector3d colorStepLeft = right?color3.minus(color1):color3.minus(color2);
				Vector3d colorStepRight = right?color3.minus(color2):color3.minus(color1);

				colorStepLeft.scale(right?1/dy31:1/dy32);
				colorStepRight.scale(right?1/dy32:1/dy31);
				
				zLeft += subPixelY*dzLeft;
				zRight += subPixelY*dzRight;
				
				for(int y=startY; y <= endY; y++)
				{
					int startX = (int)Math.ceil(xLeft);
					int endX = (int)Math.ceil(xRight) - 1;
					int xSpan = endX - startX + 1;
					
					if(xSpan > 0)
					{
						float dz = (zRight - zLeft)/ xSpan;
						float z = zLeft;
						float subPixelX = startX - xLeft;
						z += subPixelX*dz;
						
						Vector3d _color = new Vector3d(colorLeft);
						Vector3d _colorStep = colorRight.minus(colorLeft);
						_colorStep.scale(1.0f/xSpan);
						
						Vector2d texturePositionStep = texturePositionRight.minus(texturePositionLeft);
						texturePositionStep.scale(1.0f/xSpan);
						Vector2d texturePosition = new Vector2d(texturePositionLeft);
						for(int x=startX; x <= endX; x++)
						{
							float _z = zBuffer.getZ(x, y);
							if(zBuffer.zBufferComparer.compare(_z, z))
							{
								Vector2d _texturePosition = new Vector2d(texturePosition);
								
								if(projectionCorrect)
								{
									_texturePosition.scale(1/z);									
								}
								
								int textureX = (int)(_texturePosition.x * (bitmap.getWidth()-1));
								int textureY = (int)(_texturePosition.y * (bitmap.getHeight()-1));
								
								textureX = clamp(textureX, 0, bitmap.getWidth() - 1);
								textureY = clamp(textureY, 0, bitmap.getHeight() - 1);
								
								int textureColor = bitmap.getPixel(textureX, textureY);
								pixelRenderer.setPixel(x, y, textureColor);
								//pixelRenderer.setPixel(x, y, _color.asColor());
								//pixelRenderer.setPixel(x, y, right?0xffff0000:0xff00ff00);
								zBuffer.setZ(x, y, z);
							}
							
							z += dz;
							_color = _color.add(_colorStep);
							texturePosition = texturePosition.add(texturePositionStep);
						}
					}
					
					xLeft += dxLeft;
					xRight += dxRight;
					
					zLeft += dzLeft;
					zRight += dzRight;
					
					colorLeft = colorLeft.add(colorStepLeft);
					colorRight = colorRight.add(colorStepRight);
					
					texturePositionLeft = texturePositionLeft.add(dTexturePositionLeft);
					texturePositionRight = texturePositionRight.add(dTexturePositionRight);
				}	
			}
		}
		
//		p1.z = p1.w;
//		p2.z = p2.w;
//		p3.z = p3.w;
//		
//		drawLine3d(p1.degenerate(), p3.degenerate(),0xff000000);
//		drawLine3d(p1.degenerate(), p2.degenerate(), 0xff000000);
//		drawLine3d(p2.degenerate(), p3.degenerate(), 0xff000000);
		
	}
	
	public void drawLine3d(Vector3d p1, Vector3d p2, int color)
	{
		Vector3d temp;
		if(p1.y > p2.y)
		{
			temp = p1;
			p1 = p2;
			p2 = temp;
		}
		
		float dy21 = p2.y - p1.y;
		if(dy21 <= 0)
		{
			return;
		}
		
		int topY = (int)Math.ceil(p1.y);
		int bottomY = (int)Math.ceil(p2.y) - 1;
		int ySpan = bottomY - topY + 1;
		if(ySpan <= 0)
		{
			return;
		}
		
		float subPixelY = topY - p1.y;
		float dx = (p2.x - p1.x) / dy21;
		float xStart = p1.x + subPixelY*dx; 
		
		float z = projectionCorrect? 1/p1.z : p1.z;
		
		float dz = projectionCorrect ? (1/p2.z - 1/p1.z)/dy21 : (p2.z - p1.z) / dy21;
		z += dz*subPixelY;
		for(int y=topY; y <= bottomY; y++)
		{
			//int x = (int)Math.ceil(xStart);
			int x = (int)(xStart + 0.5);
			//float subPixelX = x - xStart;
			//z += subPixelX*;
			float _z = zBuffer.getZ(x, y);
			if(zBuffer.zBufferComparer.compare(_z, z))
			{
				pixelRenderer.setPixel(x, y, color);
				zBuffer.setZ(x, y, z);
			}
			
			xStart += dx;
			z += dz;
		}
	}
	
	public int clamp(int v, int min, int max)
	{
		v = Math.min(max, v);
		v = Math.max(min, v);
		return v;
	}
	
	/*
	public void fillTriangle2(Vertex3d v1, Vertex3d v2, Vertex3d v3, Vector3d color)
	{
		Logger.startLog("fillTriangle");
		
		float x1 = v1.position.x;
		float y1 = v1.position.y;
		float z1 = v1.position.z;

		float x2 = v2.position.x;
		float y2 = v2.position.y;
		float z2 = v2.position.z;

		float x3 = v3.position.x;
		float y3 = v3.position.y;
		float z3 = v3.position.z;

		Vector3d c1 = color;//new Vector3d(0, 0, 0xff);//softwareDriverGetPixel(driver, v1);
		Vector3d c2 = color;//new Vector3d(0, 0, 0xff);//softwareDriverGetPixel(driver, v2);
		Vector3d c3 = color;//new Vector3d(0, 0, 0xff);//softwareDriverGetPixel(driver, v3);
		if(isPointOnLine(v1.position, v2.position, v3.position))
		{
			drawLine(v1.position, v2.position);
			drawLine(v2.position, v3.position);
			drawLine(v3.position, v1.position);
		}
		else
		{
			//v1->v2 = v2 - v1
			//v1->v3 = v3 - v1
			//f(x,y) = y * (x2 - x1) - x * (y2 - y1) + x1 * y2 - x2 * y1
			
			Bounds bound = new Bounds();
			
			bound.join(x1, y1);
			bound.join(x2, y2);
			bound.join(x3, y3);

			Line2d lv1v2 = new Line2d(x1, y1, x2, y2);
			Line2d lv2v3 = new Line2d(x3, y3, x2, y2);
			Line2d lv1v3 = new Line2d(x3, y3, x1, y1);

			for(int x = (int)bound.left(); x <= bound.right(); x ++)
			{
				for(int y = (int)bound.top(); y <= bound.bottom(); y ++)
				{
					float a = lv1v2.line2dFxy(x, y) / lv1v2.line2dFxy(x3, y3);
					float b = lv2v3.line2dFxy(x, y) / lv2v3.line2dFxy(x1, y1);
					float c = lv1v3.line2dFxy(x, y) / lv1v3.line2dFxy(x2, y2);
					
					if(a > 0 && a < 1 && b > 0 && b < 1 && c > 0 && c < 1)
					{
						float z = a * z3 + b * z1 + c * z2;
						int red = (int)(a * c3.x + b * c1.x + c * c2.x);
						int green = (int)(a * c3.y + b * c1.y + c * c2.y);
						int blue = (int)(a * c3.z + b * c1.z + c * c2.z);

						//driverSetPixel(x, y, new Vector3d(red, green, blue), z);
						String key = x+""+y;
						Float zf = zBuffer.get(key);
						if(zf == null || zf > z)
						{
							gameCanvas.fillRect(x, y, x+1, y+1, red<<16|green<<8|blue, 0xff);			
							zBuffer.put(key, z);
						}
					}
					
				}
			}
		}
		
		Logger.endLog();
	}
	*/
}
