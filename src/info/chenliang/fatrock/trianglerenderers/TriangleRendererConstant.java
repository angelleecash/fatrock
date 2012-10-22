package info.chenliang.fatrock.trianglerenderers;

import info.chenliang.ds.Precision;
import info.chenliang.ds.Vector2d;
import info.chenliang.ds.Vector3d;
import info.chenliang.ds.Vector4d;
import info.chenliang.fatrock.PixelRenderer;
import info.chenliang.fatrock.Vertex3d;
import info.chenliang.fatrock.ZBuffer;

public class TriangleRendererConstant extends TriangleRenderer 
{
	
	public TriangleRendererConstant(PixelRenderer pixelRenderer, ZBuffer zBuffer, boolean projectionCorrect)
	{
		super(pixelRenderer, zBuffer, projectionCorrect, null);
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
		
		Vector3d color1 = v1.transformedColor;
		Vector3d color2 = v2.transformedColor;
		Vector3d color3 = v3.transformedColor;
		
		boolean right = cross > 0;		
		
		float dxLeft = 0.0f, dxRight = 0.0f, dzLeft=0.0f, dzRight=0.0f;
		float dz31 = p3.w - p1.w;
		float dz21 = p2.w - p1.w;
		float _dz31 = 1/p3.w - 1/p1.w;
		
		Vector3d _colorStepLeft = new Vector3d(0, 0, 0);
		Vector3d _colorStepRight = new Vector3d(0, 0, 0);
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
				
				if(projectionCorrect)
				{
					dzLeft = right ? _dz31/dy31 : _dz21/dy21;
					dzRight = right ? _dz21/dy21 : _dz31/dy31;
				}
				else
				{
					dzLeft = right ? dz31/dy31 : dz21/dy21;
					dzRight = right ? dz21/dy21 : dz31/dy31;
				}
				
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
						for(int x=startX; x <= endX; x++)
						{
							float _z = zBuffer.getZ(x, y);
							if(zBuffer.zBufferComparer.compare(_z, z))						
							{
								pixelRenderer.setPixel(x, y, _color.asColor());
								//pixelRenderer.setPixel(x, y, right?0xffff0000:0xff00ff00);
								zBuffer.setZ(x, y, z);					
							}
							
							z += dz;
							_color = _color.add(_colorStep);
						}
					}
					
					xLeft += dxLeft;
					xRight += dxRight;
					
					zLeft += dzLeft;
					zRight += dzRight;
					
					colorLeft = colorLeft.add(colorStepLeft);
					colorRight = colorRight.add(colorStepRight);
					
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
				
				if(projectionCorrect)
				{
					zLeft = right ? 1/p1.w+dy21*dzLeft : 1/p2.w;
					zRight = right ? 1/p2.w: 1/p1.w+dzRight*dy21;
				}
				else
				{
					zLeft = right ? p1.w+dy21*dzLeft : p2.w;
					zRight = right ? p2.w: p1.w+dzRight*dy21;
				}
				
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
						
						for(int x=startX; x <= endX; x++)
						{
							float _z = zBuffer.getZ(x, y);
							if(zBuffer.zBufferComparer.compare(_z, z))
							{
								pixelRenderer.setPixel(x, y, _color.asColor());
								//pixelRenderer.setPixel(x, y, right?0xffff0000:0xff00ff00);
								zBuffer.setZ(x, y, z);
							}
							
							z += dz;
							_color = _color.add(_colorStep);
							
						}
					}
					
					xLeft += dxLeft;
					xRight += dxRight;
					
					zLeft += dzLeft;
					zRight += dzRight;
					
					colorLeft = colorLeft.add(colorStepLeft);
					colorRight = colorRight.add(colorStepRight);
					
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
}
